package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.IngredientCreateDTO;
import org.labonnefranquette.data.dto.impl.IngredientUpdateDTO;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ingredient Controller", description = "Controller pour les interactions des ingrédients.")
public class IngredientController {

    @Autowired
    private GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DtoTools dtoTools;

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


    @Operation(
            summary = "Ajoute un ingrédient à la carte du restaurant",
            description = "Ajoute un ingrédient à la carte du restaurant."
    )
    @PostMapping()
    public ResponseEntity<?> addIngredient(@RequestBody IngredientCreateDTO ingredientCreateDTO,
                                           @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                           @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter un ingrédient.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            if (ingredientService.existsByName(ingredientCreateDTO.getName(), jwtUtil.extractRestaurantId(authToken))) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Un ingrédient avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            Ingredient newIngredient = dtoTools.convertToEntity(ingredientCreateDTO, Ingredient.class);
            var result = ingredientService.create(newIngredient, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "L'ingrédient \"" + result.getName() + "\" a été ajouté avec succés.");
            return new ResponseEntity<>(retMap, HttpStatus.OK);


        } catch (IllegalArgumentException e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", e.getMessage());
            return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", "Une erreur côté serveur est survenu.");
            return new ResponseEntity<>(retMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Modifie un ingrédient de la carte du restaurant",
            description = "Modifie un ingrédient de la carte du restaurant."
    )
    @PutMapping()
    public ResponseEntity<?> updateAddon(@RequestBody IngredientUpdateDTO ingredientUpdateDTO,
                                         @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                         @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour modifier un ingrédient.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            Optional<Ingredient> foundAddon = ingredientService.getByName(ingredientUpdateDTO.getName(), jwtUtil.extractRestaurantId(authToken));
            if (foundAddon.isPresent() && foundAddon.get().getId() == ingredientUpdateDTO.getId()) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Un ingrédient avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            Ingredient newIngredient = dtoTools.convertToEntity(ingredientUpdateDTO, Ingredient.class);
            var result = ingredientService.update(newIngredient.getId(), newIngredient, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "L'ingrédient \"" + result.getName() + "\" a été modifié avec succés.");
            return new ResponseEntity<>(retMap, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", e.getMessage());
            return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", "Une erreur côté serveur est survenu.");
            return new ResponseEntity<>(retMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Supprimer un ingrédient de la carte",
            description = "Supprime l'ingrédient spécifié par son id si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                    @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer un ingrédient.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            var result = ingredientService.deleteById(id);
            Map<String, String> retMap = new HashMap<>();
            if (result.isEmpty()) {
                retMap.put("Erreur", "Impossible de trouver l'ingrédient avec l'id : " + id);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "L'ingrédient \"" + result.get().getName() + "\" a été supprimé avec succés.");
            return new ResponseEntity<>(retMap, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", e.getMessage());
            return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", "Une erreur côté serveur est survenu.");
            return new ResponseEntity<>(retMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
