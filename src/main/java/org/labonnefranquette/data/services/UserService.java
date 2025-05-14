package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserUpdateDto;
import org.labonnefranquette.data.model.User;

public interface UserService {

    public User create(UserCreateDto userCreateDto) throws IllegalArgumentException;

    public User getByUsername(String username);

    public User setAdmin(User user);

    public boolean deleteByUsername(String username);

    User update(UserUpdateDto userUpdateDto) throws IllegalArgumentException;
}
