package tech.scales.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Enumeration;

@Service
public class LoadBalancerService {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerService.class);

    @Autowired
    private RestTemplate restTemplate;

    private int currentServerIndex = 0;

    public ResponseEntity<?> forwardRequest(HttpServletRequest request, String body, List<String> backendServers) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();

        String backendUrl = chooseBackendServer(backendServers);
        String targetUrl = backendUrl + path + (queryString != null ? "?" + queryString : "");
        logger.info("Forwarding {} request to {}", method, targetUrl);

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(method),
                entity,
                String.class
        );

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    private String chooseBackendServer(List<String> backendServers) {
        int numOfAvailableServers = backendServers.size();
        currentServerIndex = currentServerIndex % numOfAvailableServers;
        String chosenServer = backendServers.get(currentServerIndex);
        currentServerIndex = currentServerIndex + 1;
        return chosenServer;
    }
}