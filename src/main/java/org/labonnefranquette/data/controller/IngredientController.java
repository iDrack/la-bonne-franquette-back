package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.services.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    @Autowired
    IngredientServiceImpl ingredientService;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }

/*
Les ingrédients ne sont pas récupérer pas leur id

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        Optional<Ingredient> ingredientFound = ingredientService.findAllById(id);
        return ingredientFound.map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }*/
}
