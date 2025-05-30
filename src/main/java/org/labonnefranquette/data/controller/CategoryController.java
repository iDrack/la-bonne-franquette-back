package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.repository.CategoryRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category Controller", description = "Controller pour les interactions des catégories.")
public class CategoryController {

    @Autowired
    private GenericServiceImpl<Category, CategoryRepository, Long> categoryService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Retrouve toutes les categories
     *
     * @param authToken (obligatoire)
     * @return la liste des categories en HTTP 200
     */
    @Operation(
            summary = "Retourne les catégories",
            description = "Retourne la liste complète des catégories du restaurant."
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Category>> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return new ResponseEntity<>(categoryService.getAll(authToken), HttpStatus.OK);
    }

    @Operation(
            summary = "Supprimer une catégorie de la carte",
            description = "Supprime la catégorie spécifié par son id si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                    @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer une catégorie.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            var result = categoryService.deleteById(id);
            Map<String, String> retMap = new HashMap<>();
            if (result.isEmpty()) {
                retMap.put("Erreur", "Impossible de trouver la catégorie avec l'id : " + id);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "La catégorie \"" + result.get().getName() + "\" a été supprimer avec succés.");
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