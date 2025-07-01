package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.dto.impl.PaymentTypeDTO;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.repository.PaymentTypeRepository;
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
@RequestMapping("api/v1/payment/type")
@Tag(name = "Payment Type Controller", description = "Controller pour gérer les modes de paiements.")
public class PaymentTypeController {

    @Autowired
    private GenericServiceImpl<PaymentType, PaymentTypeRepository, Long> paymentTypeService;

    @Autowired
    private DtoTools dtoTools;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Créer un nouveau type de paiement
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return liste des PaiementType, HTTP 200 ou HTTP 404 si vide
     */
    @Operation(
            summary = "Créer un nouveau type de paiement",
            description = "Récupère tous les types de paiement gérés par l'application."
    )
    @PostMapping()
    public ResponseEntity<Map<String, String>> createPaymentType(
            @RequestBody PaymentTypeDTO paymentTypeCreateDTO,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {

        try {
            if (!jwtUtil.isAdmin(authToken)) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Vous n'avez pas les droits nécessaires pour ajouter un ingrédient.");
                return new ResponseEntity<>(retMap, HttpStatus.FORBIDDEN);
            }

            if (paymentTypeService.existsByName(paymentTypeCreateDTO.getName(), jwtUtil.extractRestaurantId(authToken))) {
                Map<String, String> retMap = new HashMap<>();
                retMap.put("Erreur", "Un type de paiement avec le même nom existe déjà.");
                return new ResponseEntity<>(retMap, HttpStatus.CONFLICT);
            }

            PaymentType newPaymentType = dtoTools.convertToEntity(paymentTypeCreateDTO, PaymentType.class);
            var result = paymentTypeService.create(newPaymentType, authToken);
            Map<String, String> retMap = new HashMap<>();

            retMap.put("Response", "Le mode de paiement \"" + result.getName() + "\" a été ajouté avec succés.");
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

    /**
     * Renvoie la liste des types de paiement disponibles.
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return liste des PaiementType, HTTP 200 ou HTTP 404 si vide
     */
    @Operation(
            summary = "Lister les types de paiement",
            description = "Récupère tous les types de paiement gérés par l'application."
    )
    @GetMapping()
    public ResponseEntity<List<PaymentType>> getAllByType(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        List<PaymentType> listPaymentType = paymentTypeService.getAll(authToken);
        return new ResponseEntity<>(listPaymentType, HttpStatus.OK);
    }
}

