package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Controller pour gérer les utilisateurs.")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private DtoTools dtoTools;

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param userCreateDto les informations de création de l’utilisateur
     * @return 201 + UserLoginDto si succès, 400 + message d’erreur sinon
     */
    @Operation(
            summary = "Sign up",
            description = "Crée un nouveau compte utilisateur avec les données fournies."
    )
    @PutMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserCreateDto userCreateDto) {
        try {
            if (!ControlInputTool.checkObject(userCreateDto, UserCreateDto.class)) {
                throw new Exception("Les informations de connexions sont invalides.");
            }
            User user = authService.signup(userCreateDto);
            if (user == null) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Informations de l'utilisateur invalides.");
                return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(dtoTools.convertToDto(user, UserLoginDto.class), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Erreur de création de compte: ", e);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Erreur", e.getMessage());
            return new ResponseEntity<>(retMap, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Authentifie un utilisateur existant.
     *
     * @param userLoginDto les identifiants (email/mot de passe)
     * @return 200 + tokens si succès, 400 + message d’erreur sinon
     */
    @Operation(
            summary = "Log in",
            description = "Authentifie l'utilisateur et renvoie un accessToken et un refreshToken."
    )
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            if (!ControlInputTool.checkObject(userLoginDto, UserLoginDto.class)) {
                throw new Exception();
            }
            Map<String, String> token = authService.authenticate(userLoginDto);
            if (token == null) {
                Map<String, String> response = new HashMap<>();
                response.put("Erreur", "Les identifiants de connexions sont incorrects.");
                System.out.println("Identifiants de connexions incorrects.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Erreur", "Les identifiants de connexions sont incorrects.");
            log.error("Erreur: ", e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Déconnecte un utilisateur.
     *
     * @param tokens map contenant accessToken et refreshToken
     * @return 200 si succès, 400 sinon
     */
    @Operation(
            summary = "Log out",
            description = "Révoque les tokens d’authentification pour déconnecter l’utilisateur."
    )
    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> tokens) {
        try {
            if (!ControlInputTool.checkToken(tokens)) {
                throw new Exception();
            }
            authService.logout(tokens.get("accessToken"), tokens.get("refreshToken"));
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Deconnexion reussie.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Erreur lors de la déconnexion.\"}");
        }
    }

    /**
     * Rafraîchit l’accessToken en utilisant le refreshToken.
     *
     * @param refreshToken map contenant le refreshToken (obligatoire)
     * @param authToken    ancien accessToken en header (obligatoire)
     * @return 200 + nouveau accessToken si succès, 400 sinon
     */
    @Operation(
            summary = "Refresh token",
            description = "Génère un nouveau accessToken à partir d’un refreshToken valide."
    )
    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<String> refreshToken(@RequestBody(required = false) Map<String, String> refreshToken,
                                               @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                               @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!ControlInputTool.checkRefreshToken(refreshToken)) {
                throw new Exception();
            }
            String newAccessToken = authService.refresh(refreshToken.get("refreshToken"));
            return ResponseEntity.ok("{\"accessToken\":\"" + newAccessToken + "\"}");
        } catch (Exception e) {
            return new ResponseEntity<>("{\"erreur\":\"Vos données ne sont pas correctes\"}", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Vérifie si l’utilisateur est connecté.
     *
     * @param authToken token d’authentification en header (obligatoire)
     * @return true si connecté, false sinon
     */
    @Operation(
            summary = "Check connection",
            description = "Teste si l’utilisateur possède une session valide."
    )
    @GetMapping(value = "/is-connected", produces = "application/json")
    public ResponseEntity<Boolean> isConnected(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        return ResponseEntity.ok(authService.isConnected());
    }
}