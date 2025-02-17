package org.labonnefranquette.data.controller.admin;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.services.UserService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Admin User Controller", description = "Controller pour les interractions des administracteurs sur la création des utilisateurs.")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserCreateDto userDto,
                                             @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                             @RequestHeader(value = "Auth-Token", required = false) String authToken) {
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
