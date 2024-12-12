package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;

import java.util.Date;

public interface UserService {

    public User createUser(UserCreateDto user);

}
