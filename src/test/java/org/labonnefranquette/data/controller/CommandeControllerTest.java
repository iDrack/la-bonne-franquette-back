package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandeControllerTest {

    @Mock
    private CommandeService commandeService;

    @Mock
    private DtoTools dtoTools;

    @Mock
    private SimpMessagingTemplate template;

    @InjectMocks
    private CommandeController commandeController;

    @Test
    public void fetchAllCommandesSuccessfully() {
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();

        when(commandeService.findAllCommande()).thenReturn(Arrays.asList(commande));
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class)).thenReturn(commandeReadDTO);

        ResponseEntity<List<CommandeReadDTO>> response = commandeController.fetchAllCommandes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void fetchAllCommandesEnCoursSuccessfully() {
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        when(commandeService.findAllCommandeWithStatut(StatusCommande.EN_COURS)).thenReturn(Arrays.asList(commande));
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class)).thenReturn(commandeReadDTO);

        ResponseEntity<List<CommandeReadDTO>> response = commandeController.fetchAllCommandesEnCours("en-cours");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void fetchAllCommandesEnCoursInvalidStatus() {
        ResponseEntity<List<CommandeReadDTO>> response = commandeController.fetchAllCommandesEnCours("invalid-status");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void fetchAllCommandesListeSuccessfully() {
        CommandeListeProjection projection = mock(CommandeListeProjection.class);
        when(commandeService.findAllCommandeListe()).thenReturn(Arrays.asList(projection));

        ResponseEntity<List<CommandeListeProjection>> response = commandeController.fetchAllCommandesListe();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void fetchCommandeByIdSuccessfully() {
        Commande commande = new Commande();
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        when(commandeService.findCommandeById(1L)).thenReturn(Optional.of(commande));
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class)).thenReturn(commandeReadDTO);

        ResponseEntity<CommandeReadDTO> response = commandeController.fetchCommandeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void fetchCommandeByIdNotFound() {
        when(commandeService.findCommandeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CommandeReadDTO> response = commandeController.fetchCommandeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createCommandeSuccessfully() {
        CommandeCreateDTO createDTO = new CommandeCreateDTO(true, new ArrayList<>(), new ArrayList<>(), StatusCommande.EN_COURS, new ArrayList<>(), 0);
        Commande commande = new Commande();
        CommandeReadDTO readDTO = new CommandeReadDTO();
        when(dtoTools.convertToEntity(createDTO, Commande.class)).thenReturn(commande);
        when(commandeService.createCommande(commande)).thenReturn(commande);
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class)).thenReturn(readDTO);

        ResponseEntity<?> response = commandeController.createCommande(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void createCommandePriceException() {
        CommandeCreateDTO createDTO = new CommandeCreateDTO(true, new ArrayList<>(), new ArrayList<>(), StatusCommande.EN_COURS, new ArrayList<>(), -1);
        when(dtoTools.convertToEntity(createDTO, Commande.class)).thenThrow(new PriceException("Price error"));

        ResponseEntity<?> response = commandeController.createCommande(createDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Price error", response.getBody());
    }

    @Test
    public void deleteCommandeSuccessfully() {
        when(commandeService.deleteCommande(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = commandeController.deleteCommande(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void updateCommandeSuccessfully() {
        Commande commande = new Commande();
        CommandeReadDTO readDTO = new CommandeReadDTO();
        when(commandeService.advanceStatusCommande(1L)).thenReturn(commande);
        when(dtoTools.convertToDto(commande, CommandeReadDTO.class)).thenReturn(readDTO);

        ResponseEntity<CommandeReadDTO> response = commandeController.updateCommande(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}