package tech.scales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.scales.service.ConfigurationService;

import java.util.Map;

@RestController()
@RequestMapping("/api/v1/config")
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;

    @GetMapping("/all")
    public String getConfig() {
        return configurationService.getConfig().toString();
    }

    @PutMapping("/update")
    public String updateConfig(@RequestBody Map<String, Integer> newConfigs) {
        return configurationService.updateConfig(newConfigs);
    }
}