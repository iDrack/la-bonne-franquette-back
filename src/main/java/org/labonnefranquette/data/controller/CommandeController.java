package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commandes")
public class CommandeController {

    private final SimpMessagingTemplate template;

    @Autowired
    public CommandeController(SimpMessagingTemplate template) {
        this.template = template;
    }
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private DtoTools dtoTools;
/*
Les commandes ne sont pas récupérés par un endpoint REST mais par un websocket
    @GetMapping
    public ResponseEntity<List<CommandeReadDTO>> fetchAllCommandes() {
        List<Commande> commandes = commandeService.findAllCommande();
        List<CommandeReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, CommandeReadDTO.class)).toList();
        return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
    }
*/

    //Utilisé lors d'affichage de l'écran de la cuisine
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CommandeReadDTO>> fetchAllCommandesEnCours(@PathVariable String status) {
        try {
            StatusCommande statusCommande = StatusCommande.valueOf(status.replace("-","_").toUpperCase());
            List<Commande> commandes = commandeService.findAllCommandeWithStatut(statusCommande);
            List<CommandeReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, CommandeReadDTO.class)).toList();
            return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
/*
Les commandes sont récupérées par un websocket
    @GetMapping("/liste")
    public ResponseEntity<List<CommandeListeProjection>> fetchAllCommandesListe() {
        List<CommandeListeProjection> commandes = commandeService.findAllCommandeListe();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }
*/

/*
Les commandes ne sont jamais récupéré par leur id
    @GetMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> fetchCommandeById(@PathVariable  Long id) {
        Optional<Commande> commande = commandeService.findCommandeById(id);
        return commande.map(value -> new ResponseEntity<>(dtoTools.convertToDto(value, CommandeReadDTO.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
*/

    //Utilisé lors de l'envoie du panier dans l'écran de prise de commande
    @PostMapping
    public ResponseEntity<?> createCommande(@RequestBody CommandeCreateDTO commandeDto) {
        try  {
            Commande commande = commandeService.createCommande(dtoTools.convertToEntity(commandeDto, Commande.class));
            CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
            this.template.convertAndSend("/socket/commande", commandeReadDTO);
            return new ResponseEntity<>(commandeReadDTO, HttpStatus.CREATED);
        } catch (PriceException priceException) {
            return new ResponseEntity<>(priceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Utilisé lors de la suppression de des commandes dans l'écran de cuisine
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommande(@PathVariable Long id) {
        return new ResponseEntity<>(commandeService.deleteCommande(id), HttpStatus.OK);
    }

    //Utilisé lors notamment lors de l'envoie de commande dans l'écran de cuisine
    @PutMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> updateCommande(@PathVariable long id) {
        Commande commande = commandeService.advanceStatusCommande(id);
        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.OK);
    }
}
