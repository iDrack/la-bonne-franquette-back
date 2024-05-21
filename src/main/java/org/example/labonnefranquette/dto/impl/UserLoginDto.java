package org.example.labonnefranquette.dto.impl;

import lombok.Data;
import org.example.labonnefranquette.dto.UserDto;

@Data
public class UserLoginDto implements UserDto {
    private String email;
    private String password;
}
