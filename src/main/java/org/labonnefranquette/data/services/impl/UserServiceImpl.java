package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreateDto userCreateDto) {

        if (!this.dataIsConformed(userCreateDto) || Boolean.TRUE.equals(this.userRepository.existsByUsername(userCreateDto.getUsername()))) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRoles(Roles.ROLE_USER);

        this.userRepository.save(user);
        return user;

    }

    Boolean dataIsConformed(UserCreateDto user) {

        if (user == null) {
            return false;
        }
        if (user.getUsername() == null || !isValidUsername(user.getUsername())) {
            return false;
        }
        return user.getPassword() != null && this.isValidPassword(user.getPassword());
    }

    boolean isValidUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        return user == null;
    }

    boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}





