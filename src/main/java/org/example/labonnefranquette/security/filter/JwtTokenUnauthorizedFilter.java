package org.example.labonnefranquette.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.labonnefranquette.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Slf4j
@Component
@Order(2)
public class JwtTokenUnauthorizedFilter implements Filter {
    @Autowired
    private AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final ApplyFilter applyFilter = new ApplyFilter(httpRequest.getRequestURL().toString());

        if (applyFilter.doFilterTwo()) {
            final HttpServletResponse httpRes = (HttpServletResponse) response;
            String token = getJWTFromRequest(httpRequest);
            String roles = this.authService.getRolesFromToken(token);

            if (roles.contains("ROLE_ADMIN")) {
                httpRes.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }
    private String getJWTFromRequest(HttpServletRequest request) {
        return request.getHeader("auth-token");
    }


}


