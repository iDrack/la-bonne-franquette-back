package org.example.labonnefranquette.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.labonnefranquette.model.User;
import org.example.labonnefranquette.security.TokenGenerator;
import org.example.labonnefranquette.security.filter.application.ApplyFilter;
import org.example.labonnefranquette.security.voter.AuthorizationVoter;
import org.example.labonnefranquette.services.AuthService;
import org.example.labonnefranquette.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizedFilter implements Filter {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private AuthorizationVoter authorizationVoter;
    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpRes = (HttpServletResponse) response;
        final ApplyFilter applyFilter = new ApplyFilter(httpRequest.getRequestURL().toString());

        if (applyFilter.doFilterTwo()) {

            String token = httpRequest.getHeader("auth-token");
            String roles = this.tokenGenerator.getRolesFromToken(token);

            if (!this.authorizationVoter.vote(httpRequest.getRequestURL().toString(), roles)) {
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
}