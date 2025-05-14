package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/commandes")
@Validated
@Tag(name = "Commande Controller", description = "Controller pour les interractions des commandes.")
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
        List<CommandeReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, CommandeReadDTO.class)).toList();
        return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
    }

    //Utilisé lors d'affichage de l'écran de la cuisine
    @GetMapping(value = "/status/{status}", produces = "application/json")
    public ResponseEntity<List<CommandeReadDTO>> fetchAllCommandesEnCours(@PathVariable String status,
                                                                          @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                                          @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        try {
            if (!ControlInputTool.isValidString(status)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            StatusCommande statusCommande = StatusCommande.valueOf(status.replace("-", "_").toUpperCase());
            List<Commande> commandes = commandeService.findAllCommandeWithStatut(statusCommande, authToken);
            List<CommandeReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, CommandeReadDTO.class)).toList();
            System.out.println("CommandesDtos : " + commandesDtos);
            return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //Utilisé par la vue "Voir les commandes" de l'application
    @GetMapping("/liste/{year}-{month}-{day}")
    public ResponseEntity<List<CommandeListeProjection>> fetchAllCommandesListe(@PathVariable int day, @PathVariable int month, @PathVariable int year,
                                                                                @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                                                @RequestHeader(value = "Auth-Token") String authToken) {
        List<CommandeListeProjection> commandeList;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, 0, 0, 0);
            Date date = calendar.getTime();
            commandeList = commandeService.findAllCommandeListeProjection(date);
        } catch (Exception e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(commandeList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommandeReadDTO> fetchCommandeById(@PathVariable Long id,
                                                             @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                             @RequestHeader(value = "Auth-Token") String authToken) {
        Commande commande = null;
        try {
            commande = commandeService.findCommandeById(id);
        } catch (NullPointerException e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.OK);
    }

    //Utilisé lors de l'envoie du panier dans l'écran de prise de commande
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createCommande(@Valid @RequestBody CommandeCreateDTO commandeDto,
                                            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        try {
            if (!ControlInputTool.isValidObject(commandeDto, CommandeCreateDTO.class)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Commande commande = commandeService.createCommande(dtoTools.convertToEntity(commandeDto, Commande.class), authToken);
            CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
            this.template.convertAndSend("/socket/commande", commandeReadDTO);
            return new ResponseEntity<>(commandeReadDTO, HttpStatus.CREATED);
        } catch (PriceException priceException) {
            return new ResponseEntity<>(priceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Utilisé lors de la suppression de des commandes dans l'écran de cuisine
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Boolean> deleteCommande(@PathVariable Long id,
                                                  @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                  @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        if (!ControlInputTool.isValidNumber(id)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(commandeService.deleteCommande(id), HttpStatus.OK);
    }

    //Utilisé lors notamment lors de l'envoie de commande dans l'écran de cuisine
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CommandeReadDTO> updateCommande(@PathVariable long id,
                                                          @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                          @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        if (!ControlInputTool.isValidNumber(id)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Commande commande = commandeService.advanceStatusCommande(id);
        return new ResponseEntity<>(dtoTools.convertToDto(commande, CommandeReadDTO.class), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommandeReadDTO> patchCommande(@PathVariable long id, @RequestBody Map<String, Object> updates,
                                                         @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                         @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        System.out.println("Called");
        if (id <= 0 || updates == null) {
            log.error("Erreur : Données invalide ou nuls.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Commande updatedCommande = commandeService.patchCommande(id, updates);
        if (updatedCommande == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(updatedCommande, CommandeReadDTO.class), HttpStatus.OK);
    }
}
