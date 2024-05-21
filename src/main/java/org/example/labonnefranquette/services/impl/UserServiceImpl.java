package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.dto.impl.UserCreateDto;
import org.example.labonnefranquette.model.User;
import org.example.labonnefranquette.model.enums.Roles;
import org.example.labonnefranquette.repository.UserRepository;
import org.example.labonnefranquette.services.UserService;
import org.example.labonnefranquette.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DtoTools dtoTools;

    @Override
    public Boolean createUser(UserCreateDto userCreateDto) {

        if (!this.dataIsConformed(userCreateDto) || this.userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = dtoTools.convertToEntity(userCreateDto, User.class);
        user.setRoles(Roles.ROLE_USER);
        this.userRepository.save(user);

        return true;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return this.userRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date returnLastConnectionFromEmail(String email) {
        User user = this.findByEmail(email);
        return user.getLastConnection();
    }

    public void updateLastConnection(String email) {
        User user = this.userRepository.findByEmail(email);
        user.setLastConnection(new Date());
        this.userRepository.save(user);
    }
    private Boolean dataIsConformed(UserCreateDto user) {

        if (user == null) {
            return false;
        }
        if (user.getEmail() == null || !this.isValidEmail(user.getEmail())) {
            return false;
        }
        if (user.getPassword() == null || !this.isValidPassword(user.getPassword())) {
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}


