package org.labonnefranquette.sql.controller;

import org.labonnefranquette.sql.dto.PaiementCreateDTO;
import org.labonnefranquette.sql.dto.PaiementReadDTO;
import org.labonnefranquette.sql.model.Paiement;
import org.labonnefranquette.sql.services.PaiementService;
import org.labonnefranquette.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/paiement")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;
    @Autowired
    private DtoTools dtoTools;

    @GetMapping("/")
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
            retPaiement = paiementService.createPaiement(commandeId, paiementDTO.getType(), paiementDTO.getTicketEnvoye(), paiementDTO.getPrixPaye());
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(retPaiement, PaiementReadDTO.class), HttpStatus.OK);
    }
}