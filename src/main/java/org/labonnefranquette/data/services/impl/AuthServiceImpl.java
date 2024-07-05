package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.TokenGenerator;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public boolean checkConnected(String token) {
        switch (this.tokenGenerator.checkToken(token)) {
            case Valid -> {
                return true;
            }
            case Imminent -> {
                this.updateExpirationByLastConnection(token);
                return true;
            }
            case Expired -> {
                if (this.verifyTokenIsStillAvailable(token)) {
                    this.updateExpirationByLastConnection(token);
                    return true;
                } else {
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
    }
    @Override
    public String getUsernameFromtoken(String token) {
        return this.tokenGenerator.getUsernameByToken(token);
    }

    private Boolean verifyTokenIsStillAvailable(String token) {
        Date lastConnection = this.userService.getLastConnectionByUsername(this.getUsernameFromtoken(token));
        Date tenMinutsAgo = new Date(System.currentTimeMillis() - (10 * 60 * 1000));
        return !lastConnection.before(tenMinutsAgo);
    }

    private void updateExpirationByLastConnection(String token) {
        String username = this.getUsernameFromtoken(token);
        this.userService.setLastConnectionByUsername(username);
    }
}








