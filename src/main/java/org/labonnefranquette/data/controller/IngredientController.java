package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.services.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    @Autowired
    IngredientServiceImpl ingredientService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        Optional<Ingredient> ingredientFound = ingredientService.findAllById(id);
        return ingredientFound.map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
