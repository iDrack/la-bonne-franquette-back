package org.labonnefranquette.data.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/testConnection")
public class ConnectionTestController {
    //Utilisé lors de la connexion de l'utilisateur
    @GetMapping()
    public ResponseEntity<String> testConnection() {
        return new ResponseEntity<>("Connection successful !", HttpStatus.OK);
    }
}
