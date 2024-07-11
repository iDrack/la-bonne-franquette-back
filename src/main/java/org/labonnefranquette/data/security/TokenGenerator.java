package org.labonnefranquette.data.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenGenerator {

    private final Key key;
    private ConcurrentHashMap<String, Date> blacklistedTokens = new ConcurrentHashMap<>();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public TokenGenerator(Key key) {
        this.key = key;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles())
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 20 * 1000)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenStatus checkToken(String token) {
        if (token == null || blacklistedTokens.containsKey(token)) return TokenStatus.Invalid;
        try {
            Claims claims = this.parseToken(token);
            long expirationTime = claims.getExpiration().getTime();
            long currentTime = System.currentTimeMillis();

            if (expirationTime - currentTime <= 60 * 5 * 1000) {
                return TokenStatus.Imminent;
            }

            if (expirationTime < currentTime) {
                return TokenStatus.Expired;
            }
            return TokenStatus.Valid;
        } catch (Exception e) {
            return TokenStatus.Invalid;
        }
    }

    public boolean invalidateToken(String token) {
        try {
            Claims claims = parseToken(token);
            Date expirationDate = claims.getExpiration();
            blacklistedTokens.put(token, expirationDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)
    public void removeExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().getTime() < System.currentTimeMillis());
    }

    public String getUsernameByToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    public String getRolesByToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", String.class);
    }

}
