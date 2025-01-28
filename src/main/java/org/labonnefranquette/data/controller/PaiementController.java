package org.labonnefranquette.data.controller;

import jakarta.mail.MessagingException;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.services.MailService;
import org.labonnefranquette.data.services.PaiementService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/paiement")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    @Autowired
    private DtoTools dtoTools;
    @Autowired
    private MailService mailService;


    @GetMapping("/generatePDF/{id}")
    public ResponseEntity<String> generatePDF(@PathVariable("id") Long id) {
        try {
            Paiement paiement = paiementService.getPaiementById(id);

            paiementService.generatePDF(paiement);
            return new ResponseEntity<>("PDF généré avec succés.", HttpStatus.OK);
        } catch (IOException e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendReceipt/{id}")
    public ResponseEntity<String> sendReceipt(@PathVariable("id") Long id, @RequestBody String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            try {
                Paiement paiement = paiementService.getPaiementById(id);
                mailService.sendMailReceipt(email, paiement);
            } catch (IOException | MessagingException e) {
                System.out.println(e.toString());
                return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Facture envoyée.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("L'e-mail est invalide.", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
/*
TODO: Implémenter la gestion des paiements
Les endpoints des paiements ne sont pas encore utilisés

    @GetMapping
    public ResponseEntity<List<PaiementReadDTO>> getAllPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiement();
        List<PaiementReadDTO> resultat = paiements.stream().map(x -> dtoTools.convertToDto(x, PaiementReadDTO.class)).toList();
        return new ResponseEntity<>(resultat, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementReadDTO> getPaiementById(@PathVariable("id") Long id) {
        Optional<Paiement> paiementOptional = paiementService.getPaiementById(id);
        return paiementOptional.map(paiement -> new ResponseEntity<>(dtoTools.convertToDto(paiement, PaiementReadDTO.class), HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{commandeId}")
    public ResponseEntity<PaiementReadDTO> createNewPaiement(@RequestBody PaiementCreateDTO paiementDTO, @PathVariable Long commandeId) {
        Paiement retPaiement;
        try {
            retPaiement = paiementService.createPaiement(commandeId, paiementDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(retPaiement, PaiementReadDTO.class), HttpStatus.OK);
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<PaiementReadDTO>> getPaiementsByCommande(@PathVariable Long commandeId) {
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
