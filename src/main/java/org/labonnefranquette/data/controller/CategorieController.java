package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
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
    GenericService<Categorie, Long> categorieService;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategorie() {
        return new ResponseEntity<>(categorieService.findAll(), HttpStatus.OK);
    }

/*
Les catégories ne sont jamais appelé par leur id
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable long id) {
        Optional<Categorie> categorieFound = categorieService.findAllById(id);
        return categorieFound.map(categorie -> new ResponseEntity<>(categorie, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
*/
}
