package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Product;
import org.labonnefranquette.data.repository.ProductRepository;
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
@RequestMapping("/api/v1/product")
@Tag(name = "Product Controller", description = "Controller pour les interactions des produits.")
public class ProductController {

    @Autowired
    private GenericServiceImpl<Product, ProductRepository, Long> productService;

    /**
     * Récupère la liste de tous les produits.
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la liste des produits, HTTP 200
     */
    @Operation(
            summary = "Récupérer tous les produits",
            description = "Renvoie la liste complète des produits disponibles pour le restaurant."
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Product>> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return new ResponseEntity<>(productService.getAll(authToken), HttpStatus.OK);
    }
}