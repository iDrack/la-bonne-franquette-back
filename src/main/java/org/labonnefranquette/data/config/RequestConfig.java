package org.labonnefranquette.data.config;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.security.RequestLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.Arrays;

@Configuration
public class RequestConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLimiter requestLimiter;

    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        if (!Arrays.asList(env.getActiveProfiles()).contains("test")) {
            registry.addInterceptor(requestLimiter);
        }
    }
}
