package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cache")
@Tag(name = "Cache Controller", description = "Controller pour les interactions du cache.")
public class CacheController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    JWTUtil jwtUtil;

    /**
     * Récupère la version du cache pour le restaurant courant.
     *
     * @param authToken (obligatoire) JWT contenant l’ID du restaurant
     * @return la version du cache sous forme d’un entier, HTTP 200
     */
    @Operation(
            summary = "Get cache version",
            description = "Retourne la version actuelle du cache pour le restaurant identifié par le JWT."
    )
    @GetMapping(value = "/version", produces = "application/json")
    public ResponseEntity<Integer> getCache(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        Long idRestaurant = jwtUtil.extractRestaurantId(authToken);
        return new ResponseEntity<>(restaurantService.getVersion(idRestaurant), HttpStatus.OK);
    }

}