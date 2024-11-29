package org.labonnefranquette.data.command;

import org.labonnefranquette.data.security.service.RequestLimiterService;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DailyCleanCommand {

    @Autowired
    private RequestLimiterService requestLimiterService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearRequestTimes() {
        requestLimiterService.clearRequestTimes();

        long currentTime = System.currentTimeMillis();
        long lastRequestTime = requestLimiterService.getLastRequestTime();

        if (currentTime - lastRequestTime > TimeUnit.HOURS.toMillis(2)) {
            JwtBlacklistService.clearBlacklist();
        }
    }
}
