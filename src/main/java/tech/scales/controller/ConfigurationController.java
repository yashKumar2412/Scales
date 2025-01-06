package tech.scales.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tech.scales.model.Endpoint;
import tech.scales.service.ConfigurationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ConfigurationController {

    List<Endpoint> endpoints = new ArrayList<>();
    HashMap<String, Integer> configurations = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    ConfigurationService configurationService;


}