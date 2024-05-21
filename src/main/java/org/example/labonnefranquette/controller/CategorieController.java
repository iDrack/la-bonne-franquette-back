package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Categorie;
import org.example.labonnefranquette.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorie")
public class CategorieController {

    @Autowired
    CategorieService categorieService;

    @GetMapping("/ ")
    public ResponseEntity<String> getCategorie() {
        return new ResponseEntity<>("{\"Youpi\": \"Youpi\"  }", HttpStatus.OK);
    }
}
