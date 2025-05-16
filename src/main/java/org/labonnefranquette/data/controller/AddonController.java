package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.repository.AddonRepository;
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
@RequestMapping("/api/v1/addon")
@Tag(name = "Addon Controller", description = "Controller pour les interactions des extras.")
public class AddonController {

    @Autowired
    private GenericServiceImpl<Addon, AddonRepository, Long> addonService;

    /**
     * Récupère la liste de tous les extras.
     *
     * @param authToken (obligatoire et valide) jeton d'authentification
     * @return la liste des extras sous forme JSON, HTTP 200
     */
    @Operation(
            summary = "Lister tous les extras",
            description = "Retourne la liste de tous les extras disponibles pour le restaurant."
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Addon>> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return new ResponseEntity<>(addonService.getAll(authToken), HttpStatus.OK);
    }
}