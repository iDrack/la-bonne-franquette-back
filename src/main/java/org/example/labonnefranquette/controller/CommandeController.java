package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.dto.CommandeCreateDTO;
import org.example.labonnefranquette.dto.CommandeReadDTO;
import org.example.labonnefranquette.model.Commande;
import org.example.labonnefranquette.services.CommandeService;
import org.example.labonnefranquette.utils.DtoTools;
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
        Commande commande = commandeService.createCommande(commandeDTO.getNumero(), commandeDTO.getSurPlace(), commandeDTO.getMenuSet(), commandeDTO.getProduitsAndExtras());
        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.CREATED);
    }
}
