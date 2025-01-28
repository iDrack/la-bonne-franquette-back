package org.labonnefranquette.data.controller;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.services.MailService;
import org.labonnefranquette.data.services.PaiementService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PaiementControllerTest {

    @Mock
    private PaiementService paiementService;

    @Mock
    private MailService mailService;

    @Mock
    private DtoTools dtoTools;

    @InjectMocks
    private PaiementController paiementController;

    @Test
    public void sendReceiptSuccessfully() throws IOException, MessagingException {
        String email = "valid.email@example.com";
        Paiement paiement = new Paiement();
        when(paiementService.getPaiementById(1L)).thenReturn(paiement);

        ResponseEntity<String> response = paiementController.sendReceipt(1L, email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Facture envoyée.", response.getBody());
    }

    @Test
    public void sendReceiptInvalidEmail() {
        String email = "invalid-email";

        ResponseEntity<String> response = paiementController.sendReceipt(1L, email);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("L'e-mail est invalide.", response.getBody());
    }

    @Test
    public void sendReceiptMessagingException() throws IOException, MessagingException {
        String email = "valid.email@example.com";
        Paiement paiement = new Paiement();
        when(paiementService.getPaiementById(1L)).thenReturn(paiement);
        doThrow(new MessagingException()).when(mailService).sendMailReceipt(email, paiement);

        ResponseEntity<String> response = paiementController.sendReceipt(1L, email);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
/*TODO

    @Test
    public void getAllPaiementsSuccessfully() {
        Paiement paiement = new Paiement();
        PaiementReadDTO paiementReadDTO = new PaiementReadDTO();
        when(paiementService.getAllPaiement()).thenReturn(Arrays.asList(paiement));
        when(dtoTools.convertToDto(paiement, PaiementReadDTO.class)).thenReturn(paiementReadDTO);

        ResponseEntity<List<PaiementReadDTO>> response = paiementController.getAllPaiements();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getPaiementByIdSuccessfully() {
        Paiement paiement = new Paiement();
        PaiementReadDTO paiementReadDTO = new PaiementReadDTO();
        when(paiementService.getPaiementById(1L)).thenReturn(Optional.of(paiement));
        when(dtoTools.convertToDto(paiement, PaiementReadDTO.class)).thenReturn(paiementReadDTO);

        ResponseEntity<PaiementReadDTO> response = paiementController.getPaiementById(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getPaiementByIdNotFound() {
        when(paiementService.getPaiementById(1L)).thenReturn(Optional.empty());

        ResponseEntity<PaiementReadDTO> response = paiementController.getPaiementById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void createNewPaiementSuccessfully() {
        PaiementCreateDTO paiementCreateDTO = new PaiementCreateDTO();
        Paiement paiement = new Paiement();
        PaiementReadDTO paiementReadDTO = new PaiementReadDTO();
        when(paiementService.createPaiement(1L, paiementCreateDTO)).thenReturn(paiement);
        when(dtoTools.convertToDto(paiement, PaiementReadDTO.class)).thenReturn(paiementReadDTO);

        ResponseEntity<PaiementReadDTO> response = paiementController.createNewPaiement(paiementCreateDTO, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void createNewPaiementNotFound() {
        PaiementCreateDTO paiementCreateDTO = new PaiementCreateDTO();
        when(paiementService.createPaiement(1L, paiementCreateDTO)).thenThrow(new RuntimeException());

        ResponseEntity<PaiementReadDTO> response = paiementController.createNewPaiement(paiementCreateDTO, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void getPaiementsByCommandeSuccessfully() {
        Paiement paiement = new Paiement();
        PaiementReadDTO paiementReadDTO = new PaiementReadDTO();
        when(paiementService.getPaiementByCommande(1L)).thenReturn(Arrays.asList(paiement));
        when(dtoTools.convertToDto(paiement, PaiementReadDTO.class)).thenReturn(paiementReadDTO);

        ResponseEntity<List<PaiementReadDTO>> response = paiementController.getPaiementsByCommande(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getPaiementsByCommandeNotFound() {
        when(paiementService.getPaiementByCommande(1L)).thenThrow(new RuntimeException());

        ResponseEntity<List<PaiementReadDTO>> response = paiementController.getPaiementsByCommande(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }*/
}