package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<List<CommandeReadDTO>> fetchAllCommandes() {
        List<Commande> commandes = commandeService.findAllCommande();
        if (commandes == null || commandes.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<CommandeReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, CommandeReadDTO.class)).toList();
        return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
    }

    @GetMapping("/liste")
    public ResponseEntity<List<CommandeListeProjection>> fetchAllCommandesListe() {
        List<CommandeListeProjection> commandes = commandeService.findAllCommandeListe();
        if (commandes == null || commandes.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> fetchCommandeById(long id) {
        Optional<Commande> commande = commandeService.findCommandeById(id);
        return commande.map(value -> new ResponseEntity<>(dtoTools.convertToDto(value, CommandeReadDTO.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createCommande(@RequestBody CommandeCreateDTO commandeDto) {
        try  {
            Commande commande = commandeService.createCommande(dtoTools.convertToEntity(commandeDto, Commande.class));
            this.template.convertAndSend("/socket/commande", commande);
            return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.CREATED);
        } catch (PriceException priceException) {
            return new ResponseEntity<>(priceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommande(long id) {
        if (commandeService.deleteCommande(id))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> updateCommande(@PathVariable long id) {
        Commande commande = commandeService.advanceStatusCommande(id);

        if (commande == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.OK);
    }
}
