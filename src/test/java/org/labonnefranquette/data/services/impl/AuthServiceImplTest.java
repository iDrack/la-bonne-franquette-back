package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.security.service.CustomUserDetailsService;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.labonnefranquette.data.services.UserService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private UserService userService;
    @Mock
    private DtoTools dtoTools;
    @Mock
    private User user;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void logoutSuccessfully() {
        // Arrange
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        // Act
        authService.logout(accessToken, refreshToken);
        // Assert
        assertTrue(JwtBlacklistService.isBlacklisted(accessToken));
        assertTrue(JwtBlacklistService.isBlacklisted(refreshToken));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void refreshSuccessfully() {
        // Arrange
        String refreshToken = "validRefreshToken";
        Restaurant restaurant = mock(Restaurant.class);

        when(jwtUtil.isValidRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtil.extractUsername(refreshToken)).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userService.getByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyList(), anyLong())).thenReturn("newAccessToken");
        when(restaurant.getId()).thenReturn(1L);
        when(user.getRestaurant()).thenReturn(restaurant);
        // Act
        String newAccessToken = authService.refresh(refreshToken);

        // Assert
        assertNotNull(newAccessToken);
        assertEquals("newAccessToken", newAccessToken);
    }

    @Test
    public void isConnectedSuccessfully() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Act
        boolean isConnected = authService.isConnected();
        // Assert
        assertTrue(isConnected);
    }

    @Test
    public void isConnectedFail() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(null);
        // Act
        boolean isConnected = authService.isConnected();
        // Assert
        assertFalse(isConnected);
    }
}
