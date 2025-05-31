package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.ProductCreateDTO;
import org.labonnefranquette.data.model.Product;
import org.labonnefranquette.data.repository.ProductRepository;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product Controller", description = "Controller pour les interactions des produits.")
public class ProductController {

    @Autowired
    private GenericServiceImpl<Product, ProductRepository, Long> productService;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DtoTools dtoTools;

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

    @Operation(
            summary = "Ajoute un produit à la carte du restaurant",
            description = "Ajoute un produit à la carte du restaurant."
    )
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductCreateDTO productCreateDTO,
                                        @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                        @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter un produit.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            // Vérification de doublon
            if (productService.existsByName(productCreateDTO.getName())) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Un produit avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            Product newAddon = dtoTools.convertToEntity(productCreateDTO, Product.class);
            var result = productService.create(newAddon, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "Le produit \"" + result.getName() + "\" a été ajouté avec succés.");
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
            summary = "Supprimer un produit de la carte",
            description = "Supprime le produit spécifié par son id si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                    @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer un produit.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            var result = productService.deleteById(id);
            Map<String, String> retMap = new HashMap<>();
            if (result.isEmpty()) {
                retMap.put("Erreur", "Impossible de trouver le produit avec l'id : " + id);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "Le produit \"" + result.get().getName() + "\" a été supprimé avec succés.");
            return new ResponseEntity<>(retMap, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("e :", e);
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
