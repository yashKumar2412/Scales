package tech.scales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HeartbeatService {

    Map<String, String> serverStatus;
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatService.class);

    @Value("${config.heartbeat-queue.type}")
    private String queueType;

    @Value("${config.heartbeat-queue.url}")
    private String queueUrl;

    public void checkHeartbeatQueue() {
        logger.info("Reading from {} HeartbeatQueue: {}", queueType, queueUrl);
    }
}
