package tech.scales.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LoadBalancerService {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerService.class);

    @Autowired
    private RestTemplate restTemplate;

    private int currentServerIndex = 0;

    public ResponseEntity<?> forwardRequest(String path, String method, String queryString, String body, HttpServletRequest request, List<String> backendServers) {
        String backendUrl = chooseBackendServer(backendServers);
        String targetUrl = backendUrl + path + (queryString != null ? "?" + queryString : "");
        logger.info("Forwarding {} request to {}", method, targetUrl);
        return ResponseEntity.of(Optional.of(targetUrl));
    }

    private String chooseBackendServer(List<String> backendServers) {
        int numOfAvailableServers = backendServers.size();
        currentServerIndex = currentServerIndex % numOfAvailableServers;
        String chosenServer = backendServers.get(currentServerIndex);
        currentServerIndex = currentServerIndex + 1;
        return chosenServer;
    }
}