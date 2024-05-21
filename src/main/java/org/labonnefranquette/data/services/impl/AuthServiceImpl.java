package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.TokenStatus;
import org.labonnefranquette.data.security.TokenGenerator;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
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







