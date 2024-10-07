package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.services.PaiementService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaiementControllerTest {

    @Mock
    private PaiementService paiementService;

    @Mock
    private DtoTools dtoTools;

    @InjectMocks
    private PaiementController paiementController;
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