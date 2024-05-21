package org.example.labonnefranquette.dto.impl;

import lombok.Getter;
import lombok.Setter;
import org.example.labonnefranquette.dto.UserDto;

@Getter
@Setter
public class UserCreateDto implements UserDto {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "user="+this.username+".";
    }
}
