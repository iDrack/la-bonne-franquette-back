package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
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
@RequestMapping("/api/v1/categorie")
@Tag(name = "Categorie Controller", description = "Controller pour les interractions des catégories.")
public class CategorieController {

    @Autowired
    private GenericServiceImpl<Categorie, CategorieRepository, Long> categorieService;

    // Utilisé lors de l'initialisation de l'application
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Categorie>> getAllCategorie(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        return new ResponseEntity<>(categorieService.findAll(authToken), HttpStatus.OK);
    }

/*
Les catégories ne sont jamais appelées par leur id
    @GetMapping("/{id}", produces = "application/json")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable long id) {
        Optional<Categorie> categorieFound = categorieService.findAllById(id);
        return categorieFound.map(categorie -> new ResponseEntity<>(categorie, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
*/
}