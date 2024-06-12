package org.labonnefranquette.data.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenGenerator {

    private final Key key;
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

    public TokenStatus verifyToken(String token) {
        if (token == null) return TokenStatus.Invalid;
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

    public String getUsernameFromtoken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    public String getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", String.class);
    }

}
