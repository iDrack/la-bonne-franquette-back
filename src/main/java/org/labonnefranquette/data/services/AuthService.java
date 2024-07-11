package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.enums.TokenStatus;

public interface AuthService {

    public String login(UserLoginDto userLoginDto);
    public boolean logout(String token);
    public boolean checkConnected(String token);
    public String getUsernameFromtoken(String username);
}
