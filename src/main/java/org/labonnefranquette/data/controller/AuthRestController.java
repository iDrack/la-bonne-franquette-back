package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.enums.TokenStatus;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {
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

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader(value = "auth-token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("{\"FORBIDDEN\":\"Accès refusé\"}", HttpStatus.FORBIDDEN);
        }
        TokenStatus tokenStatus = this.authService.verifyToken(token);
        return new ResponseEntity<>("{\"status\":\"" + tokenStatus.name() + "\"}", HttpStatus.OK);
    }

    @PostMapping("/update-token")
    public ResponseEntity<String> refreshToken(@RequestBody UserLoginDto userLoginDto, @RequestHeader(value = "auth-token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("{\"FORBIDDEN\":\"Accès refusé\"}", HttpStatus.FORBIDDEN);
        }
        String newToken = this.authService.refreshToken(userLoginDto, token);
        if (newToken == null) {
            return new ResponseEntity<>("{\"FORBIDDEN\":\"Vos données ne sont pas correctes\"}", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("{\"token\":\"" + newToken + "\"}", HttpStatus.OK);
    }


}