package tech.scales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.scales.scheduler.HealthCheckScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HealthCheckService {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void testScheduler() {
        logger.info("Time: {}", dateFormat.format(new Date()));
    }
}