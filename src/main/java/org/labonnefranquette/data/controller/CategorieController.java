package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categorie")
public class CategorieController {

    @Autowired
    GenericService<Categorie, Long> categorieService;

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategorie() {
        return new ResponseEntity<>(categorieService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable long id) {
        Optional<Categorie> categorieFound = categorieService.findAllById(id);
        return categorieFound.map(categorie -> new ResponseEntity<>(categorie, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
