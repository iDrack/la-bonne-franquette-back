package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.services.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/ingredient")
public class AdminIngredientController {

    @Autowired
    IngredientServiceImpl ingredientService;

    @PostMapping("/create")
    public ResponseEntity<Ingredient> createNewIngredient(@RequestBody Ingredient ingredient) {
        if (ingredient == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Ingredient>(ingredientService.create(ingredient), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
