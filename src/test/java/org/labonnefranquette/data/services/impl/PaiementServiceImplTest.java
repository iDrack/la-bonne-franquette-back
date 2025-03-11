package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.repository.PaiementRepository;
import org.labonnefranquette.data.repository.PaiementTypeRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.CommandeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaiementServiceImplTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private CommandeService commandeService;

    @Mock
    private PaiementTypeRepository paiementTypeRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private CommandeRepository commandeRepository;

    @InjectMocks
    private PaiementServiceImpl paiementService;

    @Test
    void shouldReturnAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        when(paiementRepository.findAll()).thenReturn(paiements);

        List<Paiement> result = paiementService.getAllPaiement();

        assertEquals(paiements, result);
    }

    @Test
    void shouldReturnPaiementById() {
        Paiement paiement = new Paiement();
        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));

        Paiement result = paiementService.getPaiementById(1L);

        assertEquals(paiement, result);
    }

    @Test
    void shouldThrowExceptionWhenPaiementNotFoundById() {
        when(paiementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paiementService.getPaiementById(1L));
    }

    @Test
    void shouldCreatePaiement() {
        Commande commande = new Commande();
        Paiement paiement = new Paiement();
        when(commandeService.findCommandeById(1L)).thenReturn(commande);
        when(paiementRepository.save(paiement)).thenReturn(paiement);

        Paiement result = paiementService.createPaiement(1L, paiement);

        assertEquals(paiement, result);
        verify(paiementRepository).save(paiement);
    }

    @Test
    void shouldReturnPaiementsByCommande() {
        List<Paiement> paiements = new ArrayList<>();
        when(paiementRepository.findByCommandeId(1L)).thenReturn(Optional.of(paiements));

        List<Paiement> result = paiementService.getPaiementByCommande(1L);

        assertEquals(paiements, result);
    }

    @Test
    void shouldThrowExceptionWhenNoPaiementsForCommande() {
        when(paiementRepository.findByCommandeId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paiementService.getPaiementByCommande(1L));
    }

    @Test
    void shouldReturnAllPaiementTypes() {
        List<PaiementType> paiementTypes = new ArrayList<>();
        when(paiementTypeRepository.findAll()).thenReturn(paiementTypes);

        List<PaiementType> result = paiementService.getAllPaiementType();

        assertEquals(paiementTypes, result);
    }
}