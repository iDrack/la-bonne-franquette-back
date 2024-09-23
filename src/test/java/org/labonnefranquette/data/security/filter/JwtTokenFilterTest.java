package org.labonnefranquette.data.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class JwtTokenFilterTest {

    @Mock
    private AuthService authService;
    @Mock
    private HttpServletRequest httpRequest;
    @Mock
    private HttpServletResponse httpResponse;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilter_ValidToken() throws IOException, ServletException {
        // Arrange
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/v1/testConnection"));
        when(httpRequest.getHeader("auth-token")).thenReturn("valid-token");
        when(authService.checkConnected("valid-token")).thenReturn(true);

        // Act
        jwtTokenFilter.doFilter(httpRequest, httpResponse, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    void testDoFilter_InvalidToken() throws IOException, ServletException {
        // Arrange
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/v1/menu"));
        when(httpRequest.getHeader("auth-token")).thenReturn("invalid-token");
        when(authService.checkConnected("invalid-token")).thenReturn(false);

        // Act
        jwtTokenFilter.doFilter(httpRequest, httpResponse, filterChain);

        // Assert
        verify(httpResponse, times(1)).setStatus(HttpStatus.FORBIDDEN.value());
        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }

    @Test
    void testDoFilter_NoToken() throws IOException, ServletException {
        // Arrange
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/v1/menu"));
        when(httpRequest.getHeader("auth-token")).thenReturn(null);

        // Act
        jwtTokenFilter.doFilter(httpRequest, httpResponse, filterChain);

        // Assert
        verify(httpResponse, times(1)).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }

    @Test
    void testDoFilter_NonProtectedRoute() throws IOException, ServletException {
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/v1/auth/login"));
        jwtTokenFilter.doFilter(httpRequest, httpResponse, filterChain);
        verify(filterChain, times(1)).doFilter(httpRequest, httpResponse);
    }
}
