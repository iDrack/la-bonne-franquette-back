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
import org.springframework.test.context.ActiveProfiles;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class JWTUtilsTest {

    @Mock
    private JwtBlacklistService jwtBlacklistService;
    @InjectMocks
    private JWTUtil jwtUtil;

    @BeforeEach
    public void setup() {
        Key secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        jwtUtil = new JWTUtil(secretKey);
    }

    @Test
    public void generateTokenSuccessfully() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");

        String token = jwtUtil.generateToken(username, roles, 1L);

        assertNotNull(token);
    }

    @Test
    public void generateRefreshTokenSuccessfully() {
        String username = "testUser";
        String token = jwtUtil.generateRefreshToken(username);
        assertNotNull(token);
    }

    @Test
    public void isValidAccessTokenSuccessfully() {
        String token = jwtUtil.generateToken("testUser", List.of("ROLE_USER"), 1L);
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(false);
            boolean isValid = jwtUtil.isValidAccessToken(token);
            assertTrue(isValid);
        }
    }

    @Test
    public void isValidAccessTokenFail() {
        String token = jwtUtil.generateToken("testUser", List.of("ROLE_USER"), 1L);
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(true);
            boolean isValid = jwtUtil.isValidAccessToken(token);
            assertFalse(isValid);
        }
    }

    @Test
    public void isValidRefreshTokenSuccessfully() {
        String token = jwtUtil.generateRefreshToken("testUser");
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(false);
            boolean isValid = jwtUtil.isValidRefreshToken(token);
            assertTrue(isValid);
        }
    }

    @Test
    public void isValidRefreshTokenFail() {
        String token = jwtUtil.generateRefreshToken("testUser");
        try (MockedStatic<JwtBlacklistService> mockedStatic = mockStatic(JwtBlacklistService.class)) {
            mockedStatic.when(() -> JwtBlacklistService.isBlacklisted(token)).thenReturn(true);
            boolean isValid = jwtUtil.isValidRefreshToken(token);
            assertFalse(isValid);
        }
    }

    @Test
    public void extractUsernameSuccessfully() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, List.of("ROLE_USER"), 1L);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void extractUsernameFail() {
        String token = "invalidToken";
        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(token));
    }

    @Test
    public void extractRolesSuccessfully() {
        List<String> roles = List.of("ROLE_USER");
        String token = jwtUtil.generateToken("testUser", roles, 1L);
        List<String> extractedRoles = jwtUtil.extractRoles(token);
        assertEquals(roles, extractedRoles);
    }

    @Test
    public void extractRolesFail() {
        String token = "invalidToken";
        assertThrows(JwtException.class, () -> jwtUtil.extractRoles(token));
    }

    @Test
    public void generateTokenWithRestaurantIdSuccessfully() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");
        Long restaurantId = 123L;
        String token = jwtUtil.generateToken(username, roles, restaurantId);
        assertNotNull(token);
    }

    @Test
    public void extractRestaurantIdSuccessfully() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");
        Long restaurantId = 123L;
        String token = jwtUtil.generateToken(username, roles, restaurantId);
        Long extractedRestaurantId = jwtUtil.extractRestaurantId(token);
        assertEquals(restaurantId, extractedRestaurantId);
    }

    @Test
    public void extractRestaurantIdFail() {
        String token = "invalidToken";
        assertThrows(JwtException.class, () -> jwtUtil.extractRestaurantId(token));
    }
}