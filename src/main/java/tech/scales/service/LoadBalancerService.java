package tech.scales.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancerService {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerService.class);

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> forwardRequest(String path, String method, String queryString, String body, HttpServletRequest request) {
        String backendUrl = "http://service:8081";
        String targetUrl = backendUrl + path + (queryString != null ? "?" + queryString : "");
        logger.info("Forwarding {} request to {}", method, targetUrl);
        return null;
    }
}