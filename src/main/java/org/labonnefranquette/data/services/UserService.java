package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;

public interface UserService {

    public User createUser(UserCreateDto userCreateDto) throws IllegalArgumentException;

    public User findUserByUsername(String username);

    public User setUserAdmin(User user);
}
