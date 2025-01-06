package tech.scales.scheduler;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.scales.service.ConfigurationService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class HealthCheckScheduler {

    private final SchedulerSettings schedulerSettings;
    private final ScheduledExecutorService executorService;
    private ScheduledFuture<?> scheduledTask;
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckScheduler.class);

    public HealthCheckScheduler(SchedulerSettings schedulerSettings) {
        this.schedulerSettings = schedulerSettings;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        startScheduler();
    }

    public void startScheduler() {
        scheduledTask = executorService.scheduleAtFixedRate(
                this::executeTask,
                5000,
                schedulerSettings.getFrequency(),
                TimeUnit.MILLISECONDS
        );
    }

    public synchronized void updateSettings(int frequency, int timeout, int retry) {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }

        schedulerSettings.setFrequency(frequency);
        schedulerSettings.setTimeout(timeout);
        schedulerSettings.setRetryLimit(retry);
        startScheduler();
    }

    private void executeTask() {
        logger.info("Running with " + schedulerSettings.toString());
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}