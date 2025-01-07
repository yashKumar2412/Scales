package tech.scales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HeartbeatService {

    Map<String, String> serverStatus;
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatService.class);

    @Value("${config.heartbeat-queue.type}")
    private String queueType;

    @Value("${config.heartbeat-queue.url}")
    private String queueUrl;

    public List<String> checkHeartbeatQueue() {
        logger.info("Reading from {} HeartbeatQueue: {}", queueType, queueUrl);
        List<String> backendServers = new ArrayList<>();

        backendServers.add("http://service-us-east-1");
        backendServers.add("http://service-us-east-2");

        return backendServers;
    }
}
