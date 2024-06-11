package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.enums.TokenStatus;

public interface AuthService {

    public String login(UserLoginDto userLoginDto);
    public String refreshToken(UserLoginDto userLoginDto, String token);
    public TokenStatus verifyToken(String token);
    public String getUsernameFromtoken(String username);
}
