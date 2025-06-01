package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.MenuCreateDTO;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/menu")
@Tag(name = "Menu Controller", description = "Controller pour les interactions des menus.")
public class MenuController {

    @Autowired
    private GenericServiceImpl<Menu, MenuRepository, Long> menuService;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DtoTools dtoTools;
    @Autowired
    private RestaurantService restaurantService;

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


    @Operation(
            summary = "Ajoute un menu à la carte du restaurant",
            description = "Ajoute un menu à la carte du restaurant."
    )
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody MenuCreateDTO menuCreateDTO,
                                        @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                        @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter un menu.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            // Vérification de doublon
            if (menuService.existsByName(menuCreateDTO.getName())) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Un menu avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            Menu newMenu = dtoTools.convertToEntity(menuCreateDTO, Menu.class);
            Long restaurantId = jwtUtil.extractRestaurantId(authToken);
            Optional<Restaurant> restaurantFound = restaurantService.findAllById(restaurantId);
            if (restaurantFound.isEmpty()) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Impossible de lier le menu à un restaurant.");
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }
            Restaurant restaurant = restaurantFound.get();
            if (newMenu.getMenuItems() != null) {
                newMenu.getMenuItems().forEach(menuItem -> menuItem.setMenu(newMenu));
                newMenu.getMenuItems().forEach(menuItem -> menuItem.setRestaurant(restaurant));
            }
            var result = menuService.create(newMenu, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "Le menu \"" + result.getName() + "\" a été ajouté avec succés.");
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
            summary = "Supprimer un menu de la carte",
            description = "Supprime le menu spécifié par son id si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                    @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer un menu.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            var result = menuService.deleteById(id);
            Map<String, String> retMap = new HashMap<>();
            if (result.isEmpty()) {
                retMap.put("Erreur", "Impossible de trouver le menu avec l'id : " + id);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "Le menu \"" + result.get().getName() + "\" a été supprimé avec succés.");
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
