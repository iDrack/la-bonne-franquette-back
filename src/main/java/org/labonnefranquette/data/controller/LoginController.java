package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<Map<String,String>> signup(@RequestBody(required = false) UserLoginDto userLoginDto) {
        Map<String, String> token = authService.authenticate(userLoginDto);

        if (token == null) {
            return new ResponseEntity<>(Collections.singletonMap("erreur", "Vos données ne sont pas correctes"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody(required = false) UserLoginDto userLoginDto) {
        Map<String, String> token = authService.authenticate(userLoginDto);

        if (token == null) {
            return new ResponseEntity<>(Collections.singletonMap("erreur", "Vos données ne sont pas correctes"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/logout", produces =  "application/json")
    public ResponseEntity<String> logout(@RequestBody(required = false) Map<String,String> tokens) {
        try {
            if (!tokens.isEmpty()) {
                String accessToken = tokens.get("accessToken");
                String refreshToken = tokens.get("refreshToken");
                authService.logout(accessToken, refreshToken);
                return ResponseEntity.ok("Déconnexion réussie.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la déconnexion.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la déconnexion.");
    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<String> refreshToken(@RequestBody(required = false) Map<String,String> refreshToken) {
        try {
            String token = refreshToken.get("refreshToken");
            if (token != null) {
                String newAccessToken = authService.refresh(token);
                return ResponseEntity.ok("{\"accessToken\":\"" + newAccessToken + "\"}");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("{\"erreur\":\"Vos données ne sont pas correctes\"}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("{\"erreur\":\"Vos données ne sont pas correctes\"}", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/is-connected", produces = "application/json")
    public ResponseEntity<Boolean> isConnected() {
        return ResponseEntity.ok(authService.isConnected());
    }
}