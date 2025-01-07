package tech.scales.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tech.scales.service.HeartbeatService;
import tech.scales.service.LoadBalancerService;

@RestController
public class LoadBalancerController {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerController.class);

    @Autowired
    LoadBalancerService loadBalancerService;

    @Autowired
    HeartbeatService heartbeatService;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> forwardRequest(HttpServletRequest request, @RequestBody(required = false) String body) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();

        return loadBalancerService.forwardRequest(path, method, queryString, body, request);
    }

    @Scheduled(fixedRateString = "${config.scheduler.fixed-rate}", initialDelayString = "${config.scheduler.initial-delay}")
    public void scheduledCheckHeartbeatQueue() {
        heartbeatService.checkHeartbeatQueue();
    }
}