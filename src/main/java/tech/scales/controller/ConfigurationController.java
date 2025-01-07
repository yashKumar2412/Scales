package tech.scales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.scales.model.AddServerRequest;
import tech.scales.model.Endpoint;
import tech.scales.service.ConfigurationService;

import java.util.Map;

@RestController()
@RequestMapping("/api/v1")
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;

    @GetMapping("/config")
    public String getConfig() {
        return configurationService.getConfig().toString();
    }

    @PutMapping("/config/update")
    public String updateConfig(@RequestBody Map<String, Integer> newConfigs) {
        return configurationService.updateConfig(newConfigs);
    }

    @GetMapping("/servers")
    public String getServers() {
        return configurationService.getServers().toString();
    }

    @PostMapping("/servers/add")
    public String addServer(@RequestBody AddServerRequest request) {
        return configurationService.addServer(request);
    }
}