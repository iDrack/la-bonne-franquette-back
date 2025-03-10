package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.dto.impl.PaiementReadDTO;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.services.MailService;
import org.labonnefranquette.data.services.PaiementService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("api/v1/paiement")
@Tag(name = "Paiement Controller", description = "Controller pour les interractions des paiements.")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    @Autowired
    private DtoTools dtoTools;
    @Autowired
    private MailService mailService;


    @GetMapping("/generatePDF/{id}")
    public ResponseEntity<String> generatePDF(@PathVariable("id") Long id,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                              @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        try {
            Paiement paiement = paiementService.getPaiementById(id);

            paiementService.generatePDF(paiement);
            return new ResponseEntity<>("PDF généré avec succés.", HttpStatus.OK);
        } catch (IOException e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendReceipt/{id}")
    public ResponseEntity<String> sendReceipt(@PathVariable("id") Long id, @RequestBody String email, @RequestBody boolean seeDetails,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                              @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            try {
                Paiement paiement = paiementService.getPaiementById(id);
                mailService.sendMailReceipt(email, paiement, seeDetails);
            } catch (IOException | MessagingException e) {
                log.error("Erreur: ", e);
                return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Facture envoyée.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("L'e-mail est invalide.", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /*
    Ajouter une route pour envoyer les mail des paiement avec email, montant, (string) type de paiement, liste d'article, seeDetails
     */

    @GetMapping("/types")
    public ResponseEntity<List<PaiementType>> getAllPaiementsType(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        List<PaiementType> paiementTypes = paiementService.getAllPaiementType();
        if (paiementTypes.isEmpty()) return new ResponseEntity<>(paiementTypes, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(paiementTypes, HttpStatus.OK);
    }

    @PostMapping("/{commandeId}")
    public ResponseEntity<PaiementReadDTO> createNewPaiement(@RequestBody PaiementCreateDTO paiementDTO, @PathVariable Long commandeId,
                                                             @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                             @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        Paiement retPaiement;
        if (!ControlInputTool.isValidObject(paiementDTO, PaiementCreateDTO.class)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            retPaiement = paiementService.createPaiement(commandeId, dtoTools.convertToEntity(paiementDTO, Paiement.class));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(retPaiement, PaiementReadDTO.class), HttpStatus.OK);
    }
/*
TODO: Implémenter la gestion des paiements
Les endpoints des paiements ne sont pas encore utilisés

    @GetMapping
    public ResponseEntity<List<PaiementReadDTO>> getAllPaiements(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        List<Paiement> paiements = paiementService.getAllPaiement();
        List<PaiementReadDTO> resultat = paiements.stream().map(x -> dtoTools.convertToDto(x, PaiementReadDTO.class)).toList();
        return new ResponseEntity<>(resultat, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementReadDTO> getPaiementById(@PathVariable("id") Long id,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        Optional<Paiement> paiementOptional = paiementService.getPaiementById(id);
        return paiementOptional.map(paiement -> new ResponseEntity<>(dtoTools.convertToDto(paiement, PaiementReadDTO.class), HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<PaiementReadDTO>> getPaiementsByCommande(@PathVariable Long commandeId,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        List<Paiement> paiements;
        try {
            paiements = paiementService.getPaiementByCommande(commandeId);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<PaiementReadDTO> resultat = paiements.stream().map(x -> dtoTools.convertToDto(x, PaiementReadDTO.class)).toList();
        return new ResponseEntity<>(resultat, HttpStatus.FOUND);
    }*/
}
