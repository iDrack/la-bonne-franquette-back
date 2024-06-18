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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = false) UserLoginDto userLoginDto) {
        String token = this.authService.login(userLoginDto);
        if (token == null) {
            return new ResponseEntity<>("{\"FORBIDDEN\":\"Vos données ne sont pas correctes\"}", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("{\"token\":\"" + token + "\"}", HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader("auth-token") String token) {
        return new ResponseEntity<>(this.authService.logout(token), HttpStatus.OK);
    }
}