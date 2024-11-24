package org.labonnefranquette.data.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JwtBlacklistServiceTest {

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field blacklistField = JwtBlacklistService.class.getDeclaredField("blacklist");
        blacklistField.setAccessible(true);
        ((Set<String>) blacklistField.get(null)).clear();
    }

    @Test
    public void addToBlacklistSuccessfully() {
        // Arrange
        String token = "testToken";

        // Act
        JwtBlacklistService.addToBlacklist(token);

        // Assert
        assertTrue(JwtBlacklistService.isBlacklisted(token));
    }

    @Test
    public void addToBlacklistFail() {
        // Arrange
        String token = "testToken";
        String anotherToken = "anotherTestToken";

        // Act
        JwtBlacklistService.addToBlacklist(token);

        // Assert
        assertFalse(JwtBlacklistService.isBlacklisted(anotherToken));
    }

    @Test
    public void isBlacklistedSuccessfully() {
        // Arrange
        String token = "testToken";
        JwtBlacklistService.addToBlacklist(token);
        // Act
        boolean isBlacklisted = JwtBlacklistService.isBlacklisted(token);
        // Assert
        assertTrue(isBlacklisted);
    }

    @Test
    public void isBlacklistedFail() {
        // Arrange
        String token = "testToken";
        String anotherToken = "anotherTestToken";
        JwtBlacklistService.addToBlacklist(token);

        // Act
        boolean isBlacklisted = JwtBlacklistService.isBlacklisted(anotherToken);

        // Assert
        assertFalse(isBlacklisted);
    }

    @Test
    public void isNotBlacklistedSuccessfully() {
        // Arrange
        String token = "testToken";
        // Act
        boolean isBlacklisted = JwtBlacklistService.isBlacklisted(token);
        // Assert
        assertFalse(isBlacklisted);
    }

    @Test
    public void isNotBlacklistedFail() {
        // Arrange
        String token = "testToken";
        JwtBlacklistService.addToBlacklist(token);

        // Act
        boolean isBlacklisted = JwtBlacklistService.isBlacklisted(token);

        // Assert
        assertTrue(isBlacklisted);
    }
}