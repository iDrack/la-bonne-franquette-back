package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Roles;
import org.example.labonnefranquette.model.User;
import org.example.labonnefranquette.repository.UserRepository;
import org.example.labonnefranquette.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Boolean createUser(User user) {

        if (!this.dataIsConformed(user) || this.userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);

        return true;
    }


    private Boolean dataIsConformed(User user) {

        if (user == null) {
            return false;
        }
        if (user.getEmail() == null || !this.isValidEmail(user.getEmail())) {
            return false;
        }
        if (user.getPassword() == null || !this.isValidPassword(user.getPassword())) {
            return false;
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return false;
        }
        Set<Roles> roles = user.getRoles();
        for (Roles role : roles) {
            if (!Arrays.asList(Roles.values()).contains(role)) {
                return false;
            }
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


