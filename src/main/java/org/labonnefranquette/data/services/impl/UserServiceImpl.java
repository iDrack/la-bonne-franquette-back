package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean createUser(UserCreateDto userCreateDto) {

        if (!this.dataIsConformed(userCreateDto) || this.userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRoles(Roles.ROLE_USER);

        this.userRepository.save(user);

        return true;
    }

    @Override
    public User findByUsername(String username) {
        try {
            return this.userRepository.findByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date returnLastConnectionFromUsername(String username) {
        User user = this.findByUsername(username);
        return user.getLastConnection();
    }

    public void updateLastConnection(String username) {
        User user = this.userRepository.findByUsername(username);
        user.setLastConnection(new Date());
        this.userRepository.save(user);
    }

    private Boolean dataIsConformed(UserCreateDto user) {

        if (user == null) {
            return false;
        }
        if (user.getUsername() == null) {
            return false;
        }
        if (user.getPassword() == null || !this.isValidPassword(user.getPassword())) {
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }

    public User checkCredentials(String username, String password) {
        User user = this.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }
}





