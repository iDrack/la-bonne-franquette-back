package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.repository.PaiementRepository;
import org.labonnefranquette.data.services.CommandeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PaiementServiceImplTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private CommandeService commandeService;

    @InjectMocks
    private PaiementServiceImpl paiementService;

    private Paiement paiement;
    private Commande commande;

    @BeforeEach
    public void setup() {
        paiement = new Paiement();
        paiement.setId(1L);
        paiement.setType(PaiementTypeCommande.CB);
        paiement.setDate(new Date());

        commande = new Commande();
        commande.setId(1L);
        commande.setNumero((short) 123);

        paiement.setCommande(commande);
    }

    @Test
    public void getAllPaiementSuccessfully() {
        when(paiementRepository.findAll()).thenReturn(Arrays.asList(paiement));

        List<Paiement> result = paiementService.getAllPaiement();

        assertEquals(1, result.size());
        assertEquals(paiement, result.get(0));
    }

    @Test
    public void getPaiementByIdSuccessfully() {
        when(paiementRepository.findById(anyLong())).thenReturn(Optional.of(paiement));

        Paiement result = paiementService.getPaiementById(1L);

        assertEquals(paiement, result);
    }

    @Test
    public void getPaiementByIdNotFound() {
        when(paiementRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            paiementService.getPaiementById(1L);
        });
    }

    @Test
    public void createPaiementSuccessfully() {
        when(commandeService.findCommandeById(anyLong())).thenReturn(commande);
        when(paiementRepository.save(any(Paiement.class))).thenReturn(paiement);

        PaiementCreateDTO paiementCreateDTO = new PaiementCreateDTO();
        paiementCreateDTO.setType(paiement.getType());
        paiementCreateDTO.setTicketEnvoye(paiement.getTicketEnvoye());
        paiementCreateDTO.setPrixTTC(paiement.getPrixHT());

        Paiement result = paiementService.createPaiement(1L, paiementCreateDTO);
        assertEquals(paiement, result);
    }

    @Test
    public void createPaiementCommandeNotFound() {
        when(commandeService.findCommandeById(anyLong())).thenThrow(new RuntimeException("Commande not found"));

        PaiementCreateDTO paiementCreateDTO = new PaiementCreateDTO();
        paiementCreateDTO.setType(paiement.getType());
        paiementCreateDTO.setTicketEnvoye(paiement.getTicketEnvoye());
        paiementCreateDTO.setPrixTTC(paiement.getPrixHT());

        assertThrows(RuntimeException.class, () -> {
            paiementService.createPaiement(1L, paiementCreateDTO);
        });
    }

    @Test
    public void createPaiementWithNullPaiementSet() {
        when(commandeService.findCommandeById(anyLong())).thenReturn(commande);
        when(paiementRepository.save(any(Paiement.class))).thenReturn(paiement);

        commande.setPaiementSet(null);

        PaiementCreateDTO paiementCreateDTO = new PaiementCreateDTO();
        paiementCreateDTO.setType(paiement.getType());
        paiementCreateDTO.setTicketEnvoye(paiement.getTicketEnvoye());
        paiementCreateDTO.setPrixTTC(paiement.getPrixHT());

        Paiement result = paiementService.createPaiement(1L, paiementCreateDTO);
        assertNotNull(commande.getPaiementSet());
        assertEquals(1, commande.getPaiementSet().size());
        assertEquals(paiement, result);
    }

    @Test
    public void getPaiementByCommandeSuccessfully() {
        when(paiementRepository.findByCommandeId(anyLong())).thenReturn(Optional.of(Arrays.asList(paiement)));

        List<Paiement> result = paiementService.getPaiementByCommande(1L);

        assertEquals(1, result.size());
        assertEquals(paiement, result.get(0));
    }

    @Test
    public void getPaiementByCommandeNotFound() {
        when(paiementRepository.findByCommandeId(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            paiementService.getPaiementByCommande(1L);
        });
    }
}