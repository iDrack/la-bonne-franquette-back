package org.labonnefranquette.data.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.labonnefranquette.data.security.service.RequestLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLimiter implements HandlerInterceptor {

    @Autowired
    private RequestLimiterService requestLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!requestLimiterService.isRequestAllowed(request)) {
            response.setStatus(429);
            return false;
        }
        return true;
    }
}


