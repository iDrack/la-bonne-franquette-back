package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ingredient Controller", description = "Controller pour les interractions des ingrédients.")
public class IngredientController {

    @Autowired
    private GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
    }
}