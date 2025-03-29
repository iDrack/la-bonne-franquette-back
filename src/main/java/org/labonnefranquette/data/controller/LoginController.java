package org.labonnefranquette.data.controller;

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
@Tag(name = "Login Controller", description = "Controller pour la connecxion des utilisateurs et la réactualisation de leur token d'authentification.")
public class LoginController {

    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private DtoTools dtoTools;

    @PutMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserCreateDto userCreateDto) {
        try {
            if (!ControlInputTool.isValidObject(userCreateDto, UserCreateDto.class)) {
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

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            if (!ControlInputTool.isValidObject(userLoginDto, UserLoginDto.class)) {
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

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> tokens) {
        try {
            if (!ControlInputTool.isValidTokens(tokens)) {
                throw new Exception();
            }
            authService.logout(tokens.get("accessToken"), tokens.get("refreshToken"));
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Deconnexion reussie.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Erreur lors de la déconnexion.\"}");
        }
    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<String> refreshToken(@RequestBody(required = false) Map<String, String> refreshToken,
                                               @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                               @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        try {
            if (!ControlInputTool.isValidRefreshToken(refreshToken)) {
                throw new Exception();
            }
            String newAccessToken = authService.refresh(refreshToken.get("refreshToken"));
            return ResponseEntity.ok("{\"accessToken\":\"" + newAccessToken + "\"}");
        } catch (Exception e) {
            return new ResponseEntity<>("{\"erreur\":\"Vos données ne sont pas correctes\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/is-connected", produces = "application/json")
    public ResponseEntity<Boolean> isConnected(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        return ResponseEntity.ok(authService.isConnected());
    }
}