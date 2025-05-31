package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.AddonCreateDTO;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.repository.AddonRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
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
@RequestMapping("/api/v1/addon")
@Tag(name = "Addon Controller", description = "Controller pour les interactions des extras.")
public class AddonController {

    @Autowired
    private GenericServiceImpl<Addon, AddonRepository, Long> addonService;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DtoTools dtoTools;

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

    @Operation(
            summary = "Ajoute un extra à la carte du restaurant",
            description = "Ajoute un extra à la carte du restaurant."
    )
    @PostMapping()
    public ResponseEntity<?> addAddon(@RequestBody AddonCreateDTO addonCreateDTO,
                                      @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                      @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter un extra.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }
            Addon newAddon = dtoTools.convertToEntity(addonCreateDTO, Addon.class);
            var result = addonService.create(newAddon, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "L'extra \"" + result.getName() + "\" a été ajouté avec succés.");
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
            summary = "Supprimer un extra de la carte",
            description = "Supprime l'extra spécifié par son id si le JWT indique un rôle admin."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                    @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour supprimer un extra.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            var result = addonService.deleteById(id);
            Map<String, String> retMap = new HashMap<>();
            if (result.isEmpty()) {
                retMap.put("Erreur", "Impossible de trouver l'extra avec l'id : " + id);
                return new ResponseEntity<>(retMap, HttpStatus.NOT_FOUND);
            }

            retMap.put("Response", "L'extra \"" + result.get().getName() + "\" a été supprimé avec succés.");
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