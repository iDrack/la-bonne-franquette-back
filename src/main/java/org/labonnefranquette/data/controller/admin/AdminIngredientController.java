package org.labonnefranquette.data.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/ingredient")
@Tag(name = "Admin Ingredient Controller", description = "Controller pour les interractions des administracteurs sur la création des ingrédients.")
public class AdminIngredientController {

    @Autowired
    IngredientRepository repo;

    GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService = new GenericServiceImpl<>(repo);
/*
La création de nouveaux ingrédients n'est pas encore implémenté dans l'application
    @PostMapping("/create")
    public ResponseEntity<Ingredient> createNewIngredient(@RequestBody Ingredient ingredient,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (ingredient == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Ingredient>(ingredientService.create(ingredient), HttpStatus.CREATED);
    }
*/
/*
La suppression des ingrédients n'est pas encore implémenté dans l'application
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        ingredientService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    */
}
