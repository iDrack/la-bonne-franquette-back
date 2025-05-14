package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
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
@RequestMapping("/api/v1/menu")
@Tag(name = "Menu Controller", description = "Controller pour les interactions des menus.")
public class MenuController {

    @Autowired
    private GenericServiceImpl<Menu, MenuRepository, Long> menuService;

    /**
     * Récupère la liste de tous les menus.
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la liste des menus, HTTP 200
     */
    @Operation(
            summary = "Récupérer tous les menus",
            description = "Renvoie la liste complète des menus disponibles pour le restaurant."
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Menu>> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return new ResponseEntity<>(menuService.getAll(authToken), HttpStatus.OK);
    }
}