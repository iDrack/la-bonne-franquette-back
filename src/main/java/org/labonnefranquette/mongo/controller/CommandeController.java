package org.labonnefranquette.mongo.controller;

import org.labonnefranquette.mongo.dto.CommandeCreateDTO;
import org.labonnefranquette.mongo.dto.CommandeReadDTO;
import org.labonnefranquette.mongo.model.Commande;
import org.labonnefranquette.mongo.services.CommandeService;
import org.labonnefranquette.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/commande")
public class CommandeController {

    @Autowired
    CommandeService commandeService;

    @Autowired
    DtoTools dtoTools;

    @GetMapping("/")
    public ResponseEntity<List<CommandeReadDTO>> fetAllCommandes() {
        List<Commande> commandes = commandeService.findAllCommande();
        List<CommandeReadDTO> resultat = commandes.stream().map(x -> dtoTools.convertToDto(x, CommandeReadDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(resultat, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> fetchCommandesById(@PathVariable long id) {
        Optional<Commande> commandeFound = commandeService.findCommandeById(id);
        return commandeFound.map(commande -> new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<CommandeReadDTO> createCommande(@RequestBody CommandeCreateDTO commandeDTO) {
        //La route attends uniquement les id des extra et des produits, pas les informations complétes de ces entitées
        System.out.println(commandeDTO);
        Commande commandeRef = dtoTools.convertToEntity(commandeDTO, Commande.class);
        Commande commande = commandeService.createCommande(new Commande(commandeRef.getNumero(), commandeRef.getSurPlace(), commandeRef.getPrixHT(), commandeRef.getProduitsAvecExtras(), commandeRef.getMenuSet()));
        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommande(@PathVariable Long id) {
        if (commandeService.deleteCommande(id)) {
            return new ResponseEntity<>(true, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}
