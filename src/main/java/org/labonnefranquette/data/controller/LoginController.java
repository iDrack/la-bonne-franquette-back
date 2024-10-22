package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @Autowired
    private AuthServiceImpl authService;

    //Utilisé lors de la connexion de l'utilisateur
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody(required = false) UserLoginDto userLoginDto) {
        String token = this.authService.login(userLoginDto);
        if (token == null) {
            return new ResponseEntity<>("{\"FORBIDDEN\":\"Vos données ne sont pas correctes\"}", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("{\"token\":\"" + token + "\"}", HttpStatus.OK);
    }

    //Utilisé lors de la déconnexion
    @PostMapping(value = "/logout", produces = "application/plain")
    public ResponseEntity<Boolean> logout(@RequestHeader("auth-token") String token) {
        return new ResponseEntity<>(this.authService.logout(token), HttpStatus.OK);
    }

    @GetMapping(value = "/is-connected", produces = "application/plain")
    public ResponseEntity<Boolean> isConnected(@RequestHeader("auth-token") String token) {
        return new ResponseEntity<>(this.authService.checkConnected(token), HttpStatus.OK);
    }
}