package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.dto.UserDto;

@Data
public class UserLoginDto implements UserDto {
    private String username;
    private String password;
}
