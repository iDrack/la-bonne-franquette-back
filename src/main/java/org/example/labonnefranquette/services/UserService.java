package org.example.labonnefranquette.services;

import org.example.labonnefranquette.dto.impl.UserCreateDto;
import org.example.labonnefranquette.model.User;

import java.util.Date;

public interface UserService {

    public Boolean createUser(UserCreateDto user);
    public void updateLastConnection(String username);
    public User findByUsername(String username);
    public Date returnLastConnectionFromUsername(String username);
    public User checkCredentials(String username, String password);
}
