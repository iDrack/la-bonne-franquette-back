package org.labonnefranquette.data.config;

import org.labonnefranquette.data.security.service.RequestLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CommandConfig {

    @Autowired
    private RequestLimiterService requestLimiterService;
    
}