package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;

import java.util.Date;

public interface UserService {

    public Boolean createUser(UserCreateDto user);

    public void updateLastConnection(String username);

    public User findByUsername(String username);

    public Date returnLastConnectionFromUsername(String username);

    public User checkCredentials(String username, String password);
}
