package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.repository.CategoryRepository;
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
@RequestMapping("/api/v1/category")
@Tag(name = "Category Controller", description = "Controller pour les interactions des catégories.")
public class CategoryController {

    @Autowired
    private GenericServiceImpl<Category, CategoryRepository, Long> categoryService;

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
}