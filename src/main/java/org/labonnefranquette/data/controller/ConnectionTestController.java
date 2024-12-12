package org.labonnefranquette.data.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test-connection")
public class ConnectionTestController {
    //Utilisé lors de la connexion de l'utilisateur
    @GetMapping(produces = "application/json")
    public ResponseEntity<Boolean> testConnection() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
