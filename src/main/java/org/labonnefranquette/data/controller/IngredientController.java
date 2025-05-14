package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ingredient Controller", description = "Controller pour les interactions des ingrédients.")
public class IngredientController {

    @Autowired
    private GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService;

    /**
     * Récupère la liste de tous les ingrédients.
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la liste des ingrédients, HTTP 200
     */
    @Operation(
            summary = "Récupérer tous les ingrédients",
            description = "Renvoie la liste complète des ingrédients disponibles pour le restaurant."
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Ingredient>> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return new ResponseEntity<>(ingredientService.getAll(authToken), HttpStatus.OK);
    }
}