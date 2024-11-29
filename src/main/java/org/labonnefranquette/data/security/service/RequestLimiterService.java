package org.labonnefranquette.data.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RequestLimiterService {
    private final Map<String, Long> requestTimes = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;

        long currentTime = System.currentTimeMillis();
        long lastRequestTime = requestTimes.getOrDefault(key, 0L);

        if (currentTime - lastRequestTime < TimeUnit.SECONDS.toMillis(2)) {
            return false;
        }

        requestTimes.put(key, currentTime);
        return true;
    }
}