package tech.scales.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Enumeration;
import java.util.Random;

import static tech.scales.util.Constants.ALGORITHM_RANDOM;
import static tech.scales.util.Constants.ALGORITHM_ROUND_ROBIN;

@Service
public class LoadBalancerService {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${config.algorithm}")
    private String algorithm;

    private int currentServerIndex = 0;

    public ResponseEntity<?> forwardRequest(HttpServletRequest request, String body, List<String> backendServers) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();

        String backendUrl = chooseBackendServer(backendServers, algorithm);

        if (backendUrl == null) {
            logger.error("No server available.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No server available.");
        }

        String targetUrl = backendUrl + path + (queryString != null ? "?" + queryString : "");
        logger.info("Forwarding {} request to {}", method, targetUrl);

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<?> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(method),
                entity,
                Object.class
        );

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    private String chooseBackendServer(List<String> backendServers, String algorithm) {
        if (algorithm.equals(ALGORITHM_ROUND_ROBIN))
            return roundRobinSelection(backendServers);
        else if (algorithm.equals(ALGORITHM_RANDOM))
            return randomSelection(backendServers);
        else // default to random, will add more
            return randomSelection(backendServers);
    }

    private String roundRobinSelection(List<String> backendServers) {
        int numOfAvailableServers = backendServers.size();

        if (numOfAvailableServers == 0)
            return null;

        currentServerIndex = currentServerIndex % numOfAvailableServers;
        String chosenServer = backendServers.get(currentServerIndex);
        currentServerIndex = currentServerIndex + 1;
        return chosenServer;
    }

    private String randomSelection(List<String> backendServers) {
        int numOfAvailableServers = backendServers.size();

        if (numOfAvailableServers == 0)
            return null;

        Random randomNumberGenerator = new Random();
        int chosenServerIndex = randomNumberGenerator.nextInt(numOfAvailableServers);
        return backendServers.get(chosenServerIndex);
    }
}