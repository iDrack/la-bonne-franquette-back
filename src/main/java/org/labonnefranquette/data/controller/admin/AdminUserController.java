package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.services.UserService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserCreateDto userDto) {
        try {
            if (!ControlInputTool.isValidObject(userDto, UserCreateDto.class)) {
                throw new Exception();
            }
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Création réussi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Impossible de créer un utilisateur");
        }
    }

}
