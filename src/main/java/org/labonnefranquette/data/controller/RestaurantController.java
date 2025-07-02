package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.RestaurantCreateDTO;
import org.labonnefranquette.data.dto.impl.RestaurantUpdateNameDTO;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserReadDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.UserService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/restaurant")
@Tag(name = "Restaurant Controller", description = "Controller pour les interactions des restaurants.")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private DtoTools dtoTools;

    /**
     * Controller de création de Restaurant
     *
     * @param restaurantCreateDTO DTO de création de restaurant avec le nom du restaurant, le nom du responsable et son mot de passe
     * @return L'UserReadDTO du responsable de restaurant venant d'être créé.
     */
    @Operation(
            summary = "Créer un nouveau restaurant",
            description = "Créer un nouveau restaurant avec le nom passé dans le corps de la requête."
    )
    @PostMapping(path = "/create", produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.create(restaurantCreateDTO.getRestaurantName());
            User user = userService.setAdmin(
                    userService.create(new UserCreateDto(restaurantCreateDTO.getUsername(), restaurantCreateDTO.getPassword(), restaurant.getId()))
            );
            restaurantService.addUser(restaurant, user);
            return ResponseEntity.ok(dtoTools.convertToDto(user, UserReadDto.class));
        } catch (Exception e) {
            log.error("Erreur: ", e);
            if (restaurant != null) {
                restaurantService.delete(restaurant);
            }
            return ResponseEntity.badRequest().body(Map.of("Erreur", e.getMessage()));
        }
    }

    @Operation(summary = "Renomme un restaurant", description = "Renomme un restaurant avec le nom passé dans le corps de la requête.")
    @PutMapping(path = "/rename", produces = "application/json")
    public ResponseEntity<?> renameRestaurant(@RequestBody RestaurantUpdateNameDTO restaurantUpdateNameDTO,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                              @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            Long restaurantId = jwtUtil.extractRestaurantId(authToken);
            restaurantService.updateRestaurantName(restaurantId, restaurantUpdateNameDTO.getNewRestaurantName());
            return ResponseEntity.ok(Map.of("name", restaurantUpdateNameDTO.getNewRestaurantName()));
        } catch (Exception e) {
            log.error("Erreur: ", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Erreur", e.getMessage()));
        }
    }

    @Operation(summary = "Donne le nom d'un restaurant", description = "Renvoie le nom du restaurant auquel appartient l'utilisateur à l'origine de la requête.")
    @GetMapping(path = "/name", produces = "application/json")
    public ResponseEntity<?> getNameRestaurant(@Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                               @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            Long restaurantId = jwtUtil.extractRestaurantId(authToken);
            String restaurantName = restaurantService.getRestaurantNameById(restaurantId);
            return ResponseEntity.ok(Map.of("name", restaurantName));
        } catch (Exception e) {
            log.error("Erreur: ", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Erreur", e.getMessage()));
        }
    }
}