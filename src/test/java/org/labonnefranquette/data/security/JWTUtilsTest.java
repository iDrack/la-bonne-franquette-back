package org.labonnefranquette.data.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JWTUtilsTest {

    @Mock
    private JwtBlacklistService jwtBlacklistService;
    @InjectMocks
    private JWTUtil jwtUtil;

    private Key secretKey;

    @BeforeEach
    public void setup() {
        secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        jwtUtil = new JWTUtil(secretKey);
    }

    @Test
    public void generateTokenSuccessfully() {
        // Arrange
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");
        // Act
        String token = jwtUtil.generateToken(username, roles);
        // Arrange
        assertNotNull(token);
    }

    @Test
    public void generateRefreshTokenSuccessfully() {
        // Arrange
        String username = "testUser";
        // Act
        String token = jwtUtil.generateRefreshToken(username);
        // Assert
        assertNotNull(token);
    }

    @Test
    public void isValidAccessTokenSuccessfully() {
        // Arrange
        String token = jwtUtil.generateToken("testUser", List.of("ROLE_USER"));
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(false);
            // Act
            boolean isValid = jwtUtil.isValidAccessToken(token);
            // Assert
            assertTrue(isValid);
        }
    }

    @Test
    public void isValidAccessTokenFail() {
        // Arrange
        String token = jwtUtil.generateToken("testUser", List.of("ROLE_USER"));
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(true);
            // Act
            boolean isValid = jwtUtil.isValidAccessToken(token);
            // Assert
            assertFalse(isValid);
        }
    }

    @Test
    public void isValidRefreshTokenSuccessfully() {
        // Arrange
        String token = jwtUtil.generateRefreshToken("testUser");
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(false);
            // Act
            boolean isValid = jwtUtil.isValidRefreshToken(token);
            // Assert
            assertTrue(isValid);
        }
    }

    @Test
    public void isValidRefreshTokenFail() {
        // Arrange
        String token = jwtUtil.generateRefreshToken("testUser");
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(true);
            // Act
            boolean isValid = jwtUtil.isValidRefreshToken(token);
            // Assert
            assertFalse(isValid);
        }
    }

    @Test
    public void extractUsernameSuccessfully() {
        // Arrange
        String username = "testUser";
        String token = jwtUtil.generateToken(username, List.of("ROLE_USER"));

        // Act
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    public void extractUsernameFail() {
        // Arrange
        String token = "invalidToken";
        // Act & Assert
        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(token));
    }

    @Test
    public void extractRolesSuccessfully() {
        // Arrange
        List<String> roles = List.of("ROLE_USER");
        String token = jwtUtil.generateToken("testUser", roles);

        // Act
        List<String> extractedRoles = jwtUtil.extractRoles(token);

        // Assert
        assertEquals(roles, extractedRoles);
    }

    @Test
    public void extractRolesFail() {
        // Arrange
        String token = "invalidToken";
        // Act & Assert
        assertThrows(JwtException.class, () -> jwtUtil.extractRoles(token));
    }
}