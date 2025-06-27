package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserReadDto;
import org.labonnefranquette.data.dto.impl.UserUpdateDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.UserService;
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
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "Controller pour les gérer les membres d'un restaurant.")
public class UserController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private DtoTools dtoTools;

    /**
     * Récupère la liste des membres de l’équipe pour le restaurant identifié par le JWT.
     *
     * @param authToken JWT pour l'authentification et l’identification du restaurant (obligatoire)
     * @return liste des utilisateurs sous forme de UserReadDto, HTTP 200 ; HTTP 404 si aucun employé ; HTTP 500 en cas d’erreur serveur
     */
    @Operation(
            summary = "Lister les membres de l’équipe",
            description = "Renvoie tous les utilisateurs associés au restaurant extrait du JWT."
    )
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            Long restaurantId = jwtUtil.extractRestaurantId(authToken);
            List<User> team = restaurantService.getAllUser(restaurantId);
            List<UserReadDto> retList = team.stream().map(x -> dtoTools.convertToDto(x, UserReadDto.class)).toList();
            if (retList.isEmpty()) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Aucun employé trouvé.");
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(retList, HttpStatus.OK);
        } catch (NullPointerException e) {
            log.error("e: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", "Une erreur côté serveur est survenu.");
            return new ResponseEntity<>(retMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crée un nouvel utilisateur pour le restaurant si l’utilisateur est admin.
     *
     * @param authToken     JWT pour l'authentification et vérification des droits (obligatoire)
     * @param userCreateDto données de création de l’utilisateur
     * @return UserReadDto du nouvel utilisateur, HTTP 200 ; HTTP 403 si non admin ; HTTP 400 ou 500 sinon
     */
    @Operation(
            summary = "Créer un membre de l’équipe",
            description = "Ajoute un nouvel utilisateur au restaurant si le JWT indique un rôle admin."
    )
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken, @RequestBody UserCreateDto userCreateDto) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour créer un nouvel utilisateur.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }
            userCreateDto.setRestaurantId(jwtUtil.extractRestaurantId(authToken));
            User userCreated = userService.create(userCreateDto);
            UserReadDto retUser = dtoTools.convertToDto(userCreated, UserReadDto.class);
            return new ResponseEntity<>(retUser, HttpStatus.OK);
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

    /**
     * Met à jour un membre existant si l’utilisateur est admin.
     *
     * @param authToken     JWT pour l'authentification et vérification des droits (obligatoire)
     * @param userUpdateDto données de mise à jour de l’utilisateur (ancien et nouveau username)
     * @return UserReadDto mis à jour, HTTP 200 ; HTTP 403 si non admin ; HTTP 404 si utilisateur introuvable ; HTTP 400 ou 500 sinon
     */
    @Operation(
            summary = "Mettre à jour un membre de l’équipe",
            description = "Modifie les informations d’un utilisateur existant si le JWT indique un rôle admin."
    )
    @PutMapping
    public ResponseEntity<?> update(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken,
            @RequestBody UserUpdateDto userUpdateDto) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour créer un nouvel utilisateur.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            User user = userService.update(userUpdateDto);
            if (user == null) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Impossible de trouver l'utilisateur : " + userUpdateDto.getOldUsername());
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }
            UserReadDto retUser = dtoTools.convertToDto(user, UserReadDto.class);

            return new ResponseEntity<>(retUser, HttpStatus.OK);
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

    /**
     * Supprime un utilisateur par son username si l’utilisateur est admin.
     *
     * @param username  identifiant du compte à supprimer
     * @param authToken JWT pour l'authentification et vérification des droits (obligatoire)
     * @return message de confirmation, HTTP 200 ; HTTP 403 si non admin ; HTTP 404 si utilisateur introuvable ; HTTP 400 ou 500 sinon
     */
    @Operation(
            summary = "Supprimer un membre de l’équipe",
            description = "Supprime l’utilisateur spécifié si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable("username") String username,
                                        @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                        @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer un utilisateur.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            boolean result = userService.deleteByUsername(username);
            Map<String, String> retMap = new HashMap<>();
            if (!result) {
                retMap.put("Erreur", "Impossible de trouver l'utilisateur : " + username);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "L'utilisateur : " + username + " a été supprimé avec succés.");
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
}
