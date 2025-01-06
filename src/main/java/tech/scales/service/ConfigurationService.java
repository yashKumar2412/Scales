package tech.scales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.scales.controller.ConfigurationController;
import tech.scales.model.Endpoint;
import tech.scales.scheduler.HealthCheckScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.scales.util.Constants.*;

@Service
public class ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    private List<Endpoint> endpoints = new ArrayList<>();

    @Autowired
    HealthCheckScheduler scheduler;

    @Value("${config.settings.hc.frequency}")
    private int frequency;

    @Value("${config.settings.hc.timeout}")
    private int timeout;

    @Value("${config.settings.hc.retry}")
    private int retry;

    public HashMap<String, Integer> getConfig() {
        HashMap<String, Integer> configSettings = new HashMap<>();
        configSettings.put(CONFIG_HC_FREQUENCY, frequency);
        configSettings.put(CONFIG_HC_TIMEOUT, timeout);
        configSettings.put(CONFIG_HC_RETRY_LIMIT, retry);

        logger.info("Retrieved all configuration settings");
        return configSettings;
    }

    public String updateConfig(Map<String, Integer> newConfigs) {
        try {
            if (newConfigs.containsKey(CONFIG_HC_FREQUENCY))
                frequency = newConfigs.get(CONFIG_HC_FREQUENCY);

            if (newConfigs.containsKey(CONFIG_HC_TIMEOUT))
                timeout = newConfigs.get(CONFIG_HC_TIMEOUT);

            if (newConfigs.containsKey(CONFIG_HC_RETRY_LIMIT))
                retry = newConfigs.get(CONFIG_HC_RETRY_LIMIT);

            if (timeout >= frequency)
                throw new Exception("Timeout must be less than Frequency.");

            scheduler.updateSettings(frequency, timeout, retry);

            logger.info("Updated configuration settings successfully");


            return SUCCESS;
        } catch (Exception e) {
            logger.error("Failed to update configurations");
            return FAILURE + ": " + e.getMessage();
        }
    }
}