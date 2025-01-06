package tech.scales.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tech.scales.service.RoutingService;

@Controller
public class RoutingController {

    private static final Logger logger = LoggerFactory.getLogger(RoutingController.class);

    @Autowired
    RoutingService routingService;

}