package org.labonnefranquette.data.config;

import org.labonnefranquette.data.security.RequestLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class RequestConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLimiter requestLimiter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLimiter);
    }
}
