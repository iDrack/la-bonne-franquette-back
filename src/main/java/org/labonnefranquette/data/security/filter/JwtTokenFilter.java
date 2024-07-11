package org.labonnefranquette.data.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.labonnefranquette.data.model.enums.TokenStatus;
import org.labonnefranquette.data.security.filter.application.ApplyFilter;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtTokenFilter implements Filter {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final ApplyFilter applyFilter = new ApplyFilter(httpRequest.getRequestURL().toString());
        if (applyFilter.doFilterOne()) {
            final HttpServletResponse httpRes = (HttpServletResponse) response;
            String token = this.getJWTFromRequest(httpRequest);

            if (token == null) {
                httpRes.setStatus(HttpStatus.BAD_REQUEST.value());
                return;
            }

            if (!this.authService.checkConnected(token)) {
                httpRes.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        return request.getHeader("auth-token");
    }

}