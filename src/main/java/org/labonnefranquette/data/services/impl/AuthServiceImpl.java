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
            this.userService.setLastConnectionByUsername(user.getUsername());
            return this.tokenGenerator.generateToken(user);
        }

        return null;

    }
    @Override
    public boolean logout(String token) {
        return this.tokenGenerator.invalidateToken(token);
    }
    @Override
    public TokenStatus checkStatus(String token) {
        return this.tokenGenerator.checkToken(token);
    }
    @Override
    public String getUsernameFromtoken(String token) {
        return this.tokenGenerator.getUsernameByToken(token);
    }
}







