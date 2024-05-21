package org.example.labonnefranquette.services.impl;

import io.jsonwebtoken.Claims;
import org.example.labonnefranquette.dto.impl.UserLoginDto;
import org.example.labonnefranquette.model.User;
import org.example.labonnefranquette.model.enums.Roles;
import org.example.labonnefranquette.model.enums.TokenStatus;
import org.example.labonnefranquette.security.TokenGenerator;
import org.example.labonnefranquette.services.AuthService;
import org.example.labonnefranquette.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements org.example.labonnefranquette.services.AuthService {
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private UserService userService;

    @Override
    public String login(UserLoginDto userLoginDto) {

        if (userLoginDto == null) return null;
        User user = this.userService.checkCredentials(userLoginDto.getUsername(), userLoginDto.getPassword());
        if (user != null) {
            this.userService.updateLastConnection(user.getUsername());
            return this.tokenGenerator.generateToken(user);
        }

        return null;

    }
    @Override
    public String refreshToken(UserLoginDto userLoginDto, String token) {
        try {
            return switch (this.tokenGenerator.verifyToken(token)) {
                case Valid -> token;
                case Invalid, Expired -> {
                    User user = this.userService.checkCredentials(userLoginDto.getUsername(), userLoginDto.getPassword());
                    yield user != null ? this.tokenGenerator.generateToken(user) : null;
                }
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public TokenStatus verifyToken(String token) {
        return this.tokenGenerator.verifyToken(token);
    }
    @Override
    public String getUsernameFromtoken(String token) {
        return this.tokenGenerator.getUsernameFromtoken(token);
    }
}







