package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.RestaurantCreateDTO;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserReadDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/restaurant")
@Tag(name = "Restaurant Controller", description = "Controller pour les interractions des restaurants.")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    /**
     * Controller de création de Restaurant
     *
     * @param restaurantCreateDTO DTO de création de restaurant avec le nom du restaurant, le nom du responsable et son mot de passe
     * @return L'UserReadDTO du responsable de restaurant venant d'être créé.
     */
    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity<UserReadDto> createRestaurant(@Valid @RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        Restaurant restaurant;
        User user;
        try {
            restaurant = restaurantService.createRestaurant(restaurantCreateDTO.getRestaurantName());

            user = userService.createUser(new UserCreateDto(restaurantCreateDTO.getUsername(), restaurantCreateDTO.getPassword(), restaurant.getId()));
            user = userService.setUserAdmin(user);
            restaurantService.addUserToRestaurant(restaurant, user);
        } catch (Exception e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new UserReadDto(user.getUsername(), user.getRestaurant().getId()), HttpStatus.OK);
    }
}
