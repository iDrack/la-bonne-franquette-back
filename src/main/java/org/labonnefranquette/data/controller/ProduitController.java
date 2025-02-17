package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
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
@RequestMapping("/api/v1/produit")
@Tag(name = "Produit Controller", description = "Controller pour les interractions des produits.")
public class ProduitController {

    @Autowired
    private GenericServiceImpl<Produit, ProduitRepository, Long> produitService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Produit>> getAllProduits(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        return new ResponseEntity<>(produitService.findAll(), HttpStatus.OK);
    }
}