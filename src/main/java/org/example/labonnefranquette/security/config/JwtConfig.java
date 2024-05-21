package org.example.labonnefranquette.security.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.Key;

@Configuration
public class JwtConfig {

    @Bean
    @Primary
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}