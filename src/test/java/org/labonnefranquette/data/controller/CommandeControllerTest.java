package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeControllerTest {

    @Mock
    private CommandeService commandeService;

    @Mock
    private DtoTools dtoTools;

    private static final String AUTH_TOKEN = "authToken";
    @Mock
    private SimpMessagingTemplate template;
    @InjectMocks
    private CommandeController commandeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAllCommandesEnCoursWithValidStatus() {
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        when(commandeService.findAllCommandeWithStatut(StatusCommande.EN_COURS, AUTH_TOKEN))
                .thenReturn(List.of(commande));
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<List<CommandeReadDTO>> response =
                commandeController.fetchAllCommandesEnCours("EN_COURS", AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createCommandeWithValidData() {
        CommandeCreateDTO commandeCreateDTO = new CommandeCreateDTO();
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        when(dtoTools.convertToEntity(commandeCreateDTO, Commande.class))
                .thenReturn(commande);
        when(commandeService.createCommande(commande, AUTH_TOKEN))
                .thenReturn(commande);
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<?> response = commandeController.createCommande(commandeCreateDTO, AUTH_TOKEN);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(commandeReadDTO, response.getBody());
    }

    @Test
    void deleteCommandeWithValidId() {
        Long id = 1L;
        when(commandeService.deleteCommande(id)).thenReturn(true);

        ResponseEntity<Boolean> response = commandeController.deleteCommande(id, AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void updateCommandeWithValidId() {
        Long id = 1L;
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        when(commandeService.advanceStatusCommande(id)).thenReturn(commande);
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<CommandeReadDTO> response = commandeController.updateCommande(id, AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commandeReadDTO, response.getBody());
    }

    @Test
    void fetchAllCommandesEnCoursWithInvalidStatus() {
        ResponseEntity<List<CommandeReadDTO>> response = commandeController.fetchAllCommandesEnCours("INVALID_STATUS", "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void fetchAllCommandesEnCoursWithNullStatus() {
        ResponseEntity<List<CommandeReadDTO>> response = commandeController.fetchAllCommandesEnCours(null, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteCommandeWithInvalidId() {
        ResponseEntity<Boolean> response = commandeController.deleteCommande(-1L, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateCommandeWithInvalidId() {
        ResponseEntity<CommandeReadDTO> response = commandeController.updateCommande(-1L, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void patchCommandeWithNullData() {
        Long id = 1L;

        ResponseEntity<CommandeReadDTO> response = commandeController.patchCommande(id, null, AUTH_TOKEN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}