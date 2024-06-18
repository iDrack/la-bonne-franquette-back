package org.labonnefranquette.data.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.labonnefranquette.data.security.filter.application.ApplyFilter;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

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

            switch (this.authService.checkStatus(token)) {
                case Valid -> {
                } // ok
                case Imminent -> this.updateExpirationByLastConnection(token);
                case Expired -> {
                    if (this.verifyTokenIsStillAvailable(token)) {
                        this.updateExpirationByLastConnection(token);
                    } else {
                        httpRes.setStatus(HttpStatus.FORBIDDEN.value());
                        return;
                    }
                    ;
                }
                default -> {
                    httpRes.setStatus(HttpStatus.FORBIDDEN.value());
                    return;
                }
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


    private Boolean verifyTokenIsStillAvailable(String token) {
        Date lastConnection = this.userService.getLastConnectionByUsername(this.authService.getUsernameFromtoken(token));
        Date tenMinutsAgo = new Date(System.currentTimeMillis() - (10 * 60 * 1000));
        return !lastConnection.before(tenMinutsAgo);
    }

    private void updateExpirationByLastConnection(String token) {
        String username = this.authService.getUsernameFromtoken(token);
        this.userService.setLastConnectionByUsername(username);
    }
}