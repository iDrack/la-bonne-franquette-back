package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.services.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/ingredient")
public class AdminIngredientController {

    @Autowired
    IngredientServiceImpl ingredientService;
/*
La création de nouveaux ingrédients n'est pas encore implémenté dans l'application
    @PostMapping("/create")
    public ResponseEntity<Ingredient> createNewIngredient(@RequestBody Ingredient ingredient) {
        if (ingredient == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Ingredient>(ingredientService.create(ingredient), HttpStatus.CREATED);
    }
*/
/*
La suppression des ingrédients n'est pas encore implémenté dans l'application
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    */
}
