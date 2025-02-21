package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cache")
@Tag(name = "Cache Controller", description = "Controller pour les interractions du cache.")
public class CacheController {

    @Autowired
    CacheService cacheService;

    // Utilisé lors de l'initialisation de l'application
    @GetMapping(value = "/version", produces = "application/plain")
    public ResponseEntity<String> getCache(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        return new ResponseEntity<>(CacheService.getVersion(), HttpStatus.OK);
    }

    // Utilisé dans la modale de paramètre
    @GetMapping(value = "/rafraichir", produces = "application/json")
    public ResponseEntity<Boolean> refreshCache(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        return new ResponseEntity<>(cacheService.clear(), HttpStatus.OK);
    }

}