package org.example.labonnefranquette.services;

import org.example.labonnefranquette.dto.impl.UserLoginDto;
import org.example.labonnefranquette.model.enums.TokenStatus;

public interface AuthService {

    public String login(UserLoginDto userLoginDto);
    public String refreshToken(UserLoginDto userLoginDto, String token);
    public TokenStatus verifyToken(String token);
    public String getEmailFromtoken(String token);
    public String getRolesFromToken(String token);
}
