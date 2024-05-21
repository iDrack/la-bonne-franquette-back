package org.example.labonnefranquette.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.labonnefranquette.services.AuthService;
import org.example.labonnefranquette.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;

@Component
@Order(1)
@Slf4j
public class JwtTokenForbiddenFilter implements Filter {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
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

           switch (this.authService.verifyToken(token)) {
                case Valid -> { chain.doFilter(request, response); }
                case Imminent -> this.updateExpirationByLastConnection(token);
                case Expired ->   {
                    if (this.verifyTokenIsStillAvailable(token)) {
                        this.updateExpirationByLastConnection(token);
                    } else {
                        httpRes.setStatus(HttpStatus.FORBIDDEN.value());
                        return;
                    };
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
        Date lastConnection = this.userService.returnLastConnectionFromEmail(this.authService.getEmailFromtoken(token));
        Date tenMinutsAgo = new Date(System.currentTimeMillis() - (10 * 60 * 1000));
        return !lastConnection.before(tenMinutsAgo);
    }

    private void updateExpirationByLastConnection(String token) {
       String email = this.authService.getEmailFromtoken(token);
       this.userService.updateLastConnection(email);
    }
}