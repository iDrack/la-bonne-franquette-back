package org.labonnefranquette.data.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.security.TokenGenerator;
import org.labonnefranquette.data.security.voter.AuthorizationVoter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthorizedFilterTest {

    @Mock
    private TokenGenerator tokenGenerator;
    @Mock
    private AuthorizationVoter authorizationVoter;
    @Mock
    private HttpServletRequest httpRequest;
    @Mock
    private HttpServletResponse httpResponse;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private AuthorizedFilter authorizedFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilter_AdminRole() throws IOException, ServletException {
        // Arrange
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/admin/dashboard"));
        when(httpRequest.getHeader("auth-token")).thenReturn("valid-token");
        when(tokenGenerator.getRolesByToken("valid-token")).thenReturn("ROLE_ADMIN");
        when(authorizationVoter.voteAdminRole("ROLE_ADMIN")).thenReturn(true);

        authorizedFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    void testDoFilter_Forbidden() throws IOException, ServletException {
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/admin/dashboard"));
        when(httpRequest.getHeader("auth-token")).thenReturn("valid-token");
        when(tokenGenerator.getRolesByToken("valid-token")).thenReturn("ROLE_USER");
        when(authorizationVoter.voteAdminRole("ROLE_USER")).thenReturn(false);

        authorizedFilter.doFilter(httpRequest, httpResponse, filterChain);

        verify(httpResponse, times(1)).setStatus(HttpStatus.FORBIDDEN.value());
        verify(filterChain, never()).doFilter(httpRequest, httpResponse);
    }

    @Test
    void testDoFilter_NonAdminRoute() throws IOException, ServletException {
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/user/dashboard"));
        authorizedFilter.doFilter(httpRequest, httpResponse, filterChain);
        verify(filterChain, times(1)).doFilter(httpRequest, httpResponse);
    }
}
