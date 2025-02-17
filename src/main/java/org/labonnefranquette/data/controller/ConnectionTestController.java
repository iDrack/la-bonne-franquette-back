package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test-connection")
@Tag(name = "Connection Test Controller", description = "Controller pour tester la connection à l'api.")
public class ConnectionTestController {
    //Utilisé lors de la connexion de l'utilisateur
    @GetMapping(produces = "application/json")
    public ResponseEntity<Boolean> testConnection() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
