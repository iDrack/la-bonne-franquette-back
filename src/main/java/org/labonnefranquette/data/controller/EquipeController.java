package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.UserReadDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/equipe")
@Tag(name = "Équipe Controller", description = "Controller pour les gérer les memebres d'un restaurant.")
public class EquipeController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private DtoTools dtoTools;

    @GetMapping
    public ResponseEntity<?> getAllEmployees(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            Long restaurantId = jwtUtil.extractRestaurantId(authToken);
            List<User> employees = restaurantService.getAllUserFromRestaurant(restaurantId);
            List<UserReadDto> retList = employees.stream().map(x -> dtoTools.convertToDto(x, UserReadDto.class)).toList();
            if (retList.isEmpty()) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Aucun employé trouvé.");
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(retList, HttpStatus.OK);
        } catch (NullPointerException e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", "Une erreur est survenu.");
            return new ResponseEntity<>(retMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
