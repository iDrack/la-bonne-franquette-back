package org.labonnefranquette.data.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.model.enums.TokenStatus;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class TokenGeneratorTest {

    private TokenGenerator tokenGenerator;
    private Key key;
    private User user;

    @BeforeEach
    void setUp() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        tokenGenerator = new TokenGenerator(key);
        user = new User();
        user.setId((byte) 1);
        user.setUsername("testuser");
        user.setRoles(Roles.ROLE_USER);
    }

    @Test
    void testGenerateToken() {
        String token = tokenGenerator.generateToken(user);
        assertNotNull(token);
    }

    @Test
    void testParseToken() {
        String token = tokenGenerator.generateToken(user);
        Claims claims = tokenGenerator.parseToken(token);
        assertEquals("testuser", claims.get("username"));
    }

    @Test
    void testCheckToken_NullToken() {
        assertEquals(TokenStatus.Invalid, tokenGenerator.checkToken(null));
    }

    @Test
    void testCheckToken_BlacklistedToken() {
        String token = tokenGenerator.generateToken(user);
        tokenGenerator.invalidateToken(token);
        assertEquals(TokenStatus.Invalid, tokenGenerator.checkToken(token));
    }

    @Test
    void testCheckToken_ExpiredToken() throws InterruptedException {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles())
                .setExpiration(new Date(System.currentTimeMillis()  + 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        Thread.sleep(2000);
        assertEquals(TokenStatus.Expired, tokenGenerator.checkToken(token));
    }

    @Test
    void testCheckToken_ValidToken() {
        String token = tokenGenerator.generateToken(user);
        assertEquals(TokenStatus.Valid, tokenGenerator.checkToken(token));
    }

    @Test
    void testCheckToken_ImminentExpirationToken() {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles())
                .setExpiration(new Date(System.currentTimeMillis() + 4 * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        assertEquals(TokenStatus.Imminent, tokenGenerator.checkToken(token));
    }

    @Test
    void testCheckToken_InvalidToken() {
        String invalidToken = "invalid.token.here";
        assertEquals(TokenStatus.Invalid, tokenGenerator.checkToken(invalidToken));
    }

    @Test
    void testInvalidateToken() {
        String token = tokenGenerator.generateToken(user);
        assertTrue(tokenGenerator.invalidateToken(token));
        assertEquals(TokenStatus.Invalid, tokenGenerator.checkToken(token));
    }

    @Test
    void testGetUsernameByToken() {
        String token = tokenGenerator.generateToken(user);
        assertEquals("testuser", tokenGenerator.getUsernameByToken(token));
    }

    @Test
    void testGetRolesByToken() {
        String token = tokenGenerator.generateToken(user);
        assertEquals("ROLE_USER", tokenGenerator.getRolesByToken(token));
    }

    @Test
    void testRemoveExpiredTokens() throws NoSuchFieldException, IllegalAccessException {
        String token = tokenGenerator.generateToken(user);
        ConcurrentHashMap<String, Date> blacklistedTokens = new ConcurrentHashMap<>();
        blacklistedTokens.put(token, new Date(System.currentTimeMillis() - 1000));

        Field blacklistedTokensField = TokenGenerator.class.getDeclaredField("blacklistedTokens");
        blacklistedTokensField.setAccessible(true);
        blacklistedTokensField.set(tokenGenerator, blacklistedTokens);

        Field lastTokenAjouteField = TokenGenerator.class.getDeclaredField("lastTokenAjoute");
        lastTokenAjouteField.setAccessible(true);
        lastTokenAjouteField.set(tokenGenerator, new AtomicLong(System.currentTimeMillis() - (60 * 26 * 1000)));

        tokenGenerator.removeExpiredTokens();
        assertFalse(blacklistedTokens.containsKey(token));
    }

}