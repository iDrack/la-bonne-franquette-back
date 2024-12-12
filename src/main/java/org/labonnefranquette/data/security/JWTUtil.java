package org.labonnefranquette.data.security;

import io.jsonwebtoken.*;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JWTUtil {

    private final Key secretKey;

    public JWTUtil(Key secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String username, List<String> roles) {
        long expirationTime = 20 * 60 * 1000; // 20 minutes
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setAudience("access")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public String generateRefreshToken(String username) {
        long expirationTime = 24 * 60 * 60 * 1000; // 1 jour
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setAudience("refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isValidAccessToken(String token) {
        try {
            String audience = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getAudience();
            return !JwtBlacklistService.isBlacklisted(token) && Objects.equals(audience, "access");
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isValidRefreshToken(String token) {
        try {
            String audience = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getAudience();
            return !JwtBlacklistService.isBlacklisted(token) && Objects.equals(audience, "refresh");
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", List.class);
    }
}
