package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.CategoryCreateDTO;
import org.labonnefranquette.data.dto.impl.CategoryUpdateDTO;
import org.labonnefranquette.data.dto.impl.SubCategoryCreateDTO;
import org.labonnefranquette.data.dto.impl.SubCategoryUpdateDTO;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.model.SubCategory;
import org.labonnefranquette.data.repository.CategoryRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category Controller", description = "Controller pour les interractions des catégories.")
public class CategoryController {

    @Autowired
    private GenericServiceImpl<Category, CategoryRepository, Long> categoryService;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DtoTools dtoTools;

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
            summary = "Ajoute une catégorie à la carte du restaurant",
            description = "Ajoute une catégorie à la carte du restaurant."
    )
    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody CategoryCreateDTO categoryCreateDTO,
                                         @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                         @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter une catégorie.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            if (categoryService.existsByName(categoryCreateDTO.getName(), jwtUtil.extractRestaurantId(authToken))) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Une catégorie avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            if (Objects.equals(categoryCreateDTO.getCategoryType(), "category")) {
                Category newCategory = dtoTools.convertToEntity(categoryCreateDTO, Category.class);
                var category = categoryService.create(newCategory, authToken);
                Map<String, String> retMap = new HashMap<>();

                retMap.put("Response", "La catégorie \"" + category.getName() + "\" a été ajouté avec succés.");
                return new ResponseEntity<>(retMap, HttpStatus.OK);
            } else {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Données invalides");
                return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
            }

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
            summary = "Ajoute une sous-catégorie à la carte du restaurant",
            description = "Ajoute une sous-catégorie à la carte du restaurant."
    )
    @PostMapping("/sub")
    public ResponseEntity<?> addCategory(@RequestBody SubCategoryCreateDTO categoryCreateDTO,
                                         @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                         @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter une catégorie.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }
            if (categoryService.existsByName(categoryCreateDTO.getName(), jwtUtil.extractRestaurantId(authToken))) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Une catégorie avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }
            if (Objects.equals(categoryCreateDTO.getCategoryType(), "sub-category")) {
                SubCategory newCategory = dtoTools.convertToEntity(categoryCreateDTO, SubCategory.class);
                var category = categoryService.create(newCategory, authToken);
                Map<String, String> retMap = new HashMap<>();

                retMap.put("Response", "La catégorie \"" + category.getName() + "\" a été ajouté avec succés.");
                return new ResponseEntity<>(retMap, HttpStatus.OK);
            } else {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Données invalides");
                return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
            }

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
            summary = "Modifie une sous-catégorie de la carte du restaurant",
            description = "Modifie une sous-catégorie de la carte du restaurant."
    )
    @PutMapping()
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO,
                                            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour modifier une catégorie.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }
            if (categoryUpdateDTO.getCategoryType().equalsIgnoreCase("category")) {
                Optional<Category> found = categoryService.getByName(categoryUpdateDTO.getName(), jwtUtil.extractRestaurantId(authToken));
                if (found.isPresent() && found.get().getId() == categoryUpdateDTO.getId()) {
                    Map<String, String> retMap = new HashMap<>();
                    retMap.put("Erreur", "Une catégorie avec le même nom existe déjà.");
                    return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
                }
                Category newCategory = dtoTools.convertToEntity(categoryUpdateDTO, Category.class);
                var result = categoryService.update(newCategory.getId(), newCategory, authToken);
                Map<String, String> retMap = new HashMap<>();

                retMap.put("Response", "La catégorie \"" + result.getName() + "\" a été modifiée avec succés.");
                return new ResponseEntity<>(retMap, HttpStatus.OK);
            } else {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Données invalides");
                return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
            }
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
            summary = "Modifie une sous-catégorie de la carte du restaurant",
            description = "Modifie une sous-catégorie de la carte du restaurant."
    )
    @PutMapping("/sub")
    public ResponseEntity<?> updateCategory(@RequestBody SubCategoryUpdateDTO categoryUpdateDTO,
                                            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour modifier une catégorie.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            if (categoryUpdateDTO.getCategoryType().equalsIgnoreCase("sub-category")) {
                Optional<Category> found = categoryService.getByName(categoryUpdateDTO.getName(), jwtUtil.extractRestaurantId(authToken));
                if (found.isPresent() && found.get().getId() == categoryUpdateDTO.getId()) {
                    Map<String, String> retMap = new HashMap<>();
                    retMap.put("Erreur", "Une catégorie avec le même nom existe déjà.");
                    return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
                }
                Category newCategory = dtoTools.convertToEntity(categoryUpdateDTO, SubCategory.class);
                var result = categoryService.update(newCategory.getId(), newCategory, authToken);
                Map<String, String> retMap = new HashMap<>();

                retMap.put("Response", "La catégorie \"" + result.getName() + "\" a été modifiée avec succés.");
                return new ResponseEntity<>(retMap, HttpStatus.OK);
            } else {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Données invalides");
                return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
            }

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
