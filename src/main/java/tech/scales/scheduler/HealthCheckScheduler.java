package tech.scales.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class HealthCheckScheduler {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void testScheduler() {
        logger.info("Time: {}", dateFormat.format(new Date()));
    }
}