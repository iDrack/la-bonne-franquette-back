package org.labonnefranquette.data.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.security.service.CustomUserDetailsService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
        SecurityContextHolder.clearContext();
    }
    @Test
    public void doFilterInternalSuccessfullyTokenIsValid() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        when(request.getHeader("auth-token")).thenReturn(token);
        when(jwtUtil.isValidAccessToken(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(List.of());
        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternalSuccessfullyUsernameMatches() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        when(request.getHeader("auth-token")).thenReturn(token);
        when(jwtUtil.isValidAccessToken(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(List.of());
        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // Assert
        assertEquals("testUser", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Test
    public void doFilterInternalSuccessfullyAuthoritiesSize() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        when(request.getHeader("auth-token")).thenReturn(token);
        when(jwtUtil.isValidAccessToken(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(List.of());

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertEquals(0, SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());
    }

    @Test
    public void doFilterInternalFailTokenIsNull() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("auth-token")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternalFailTokenIsInvalid() throws ServletException, IOException {
        // Arrange
        String token = "invalidToken";
        when(request.getHeader("auth-token")).thenReturn(token);
        when(jwtUtil.isValidAccessToken(token)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternalFailUsernameNotFound() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        when(request.getHeader("auth-token")).thenReturn(token);
        when(jwtUtil.isValidAccessToken(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn("unknownUser");
        when(customUserDetailsService.loadUserByUsername("unknownUser")).thenThrow(new UsernameNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }
}