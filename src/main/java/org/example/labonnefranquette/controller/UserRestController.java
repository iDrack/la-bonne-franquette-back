package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Roles;
import org.example.labonnefranquette.model.User;
import org.example.labonnefranquette.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody <User> user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Création réussi");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Impossible de créer un utilisateur");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser() {
        User user = new User();
        user.setEmail("franfran@mail.com");
        user.setPassword("test");
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_ADMIN);
        user.setRoles(userRoles);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
