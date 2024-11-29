package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.labonnefranquette.data.utils.ControlInputTool;
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
    public ResponseEntity<Map<String,String>> signup(@RequestBody UserLoginDto userLoginDto) {
        try {
            if (!ControlInputTool.isValidObject(userLoginDto, UserLoginDto.class)) {
                throw new Exception();
            } 
            Map<String, String> token = authService.authenticate(userLoginDto);
            return token == null
                ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            if (!ControlInputTool.isValidObject(userLoginDto, UserLoginDto.class)) {
                throw new Exception();
            } 
            Map<String, String> token = authService.authenticate(userLoginDto);
            return token == null 
                ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout", produces =  "application/json")
    public ResponseEntity<String> logout(@RequestBody Map<String,String> tokens) {
        try {
            if (!ControlInputTool.isValidTokens(tokens)) {
                throw new Exception();
            } 
            authService.logout(tokens.get("accessToken"), tokens.get("refreshToken"));
            return ResponseEntity.status(HttpStatus.OK).body("Déconnexion réussie.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la déconnexion.");
        }
    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<String> refreshToken(@RequestBody(required = false) Map<String,String> refreshToken) {
        try {
            if (!ControlInputTool.isValidRefreshToken(refreshToken)) {
                throw new Exception();
            } 
            String newAccessToken = authService.refresh(refreshToken.get("refreshToken"));
            return ResponseEntity.ok("{\"accessToken\":\"" + newAccessToken + "\"}");
        } catch (Exception e) {
            return new ResponseEntity<>("{\"erreur\":\"Vos données ne sont pas correctes\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/is-connected", produces = "application/json")
    public ResponseEntity<Boolean> isConnected() {
        return ResponseEntity.ok(authService.isConnected());
    }
}