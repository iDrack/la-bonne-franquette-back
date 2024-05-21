package org.labonnefranquette.data.dto.impl;

import lombok.Getter;
import lombok.Setter;
import org.labonnefranquette.data.dto.UserDto;

@Getter
@Setter
public class UserCreateDto implements UserDto {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "user=" + this.username + ".";
    }
}
