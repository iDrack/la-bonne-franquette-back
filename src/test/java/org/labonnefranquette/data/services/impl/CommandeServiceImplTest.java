package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.utils.CommandeTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandeServiceImplTest {

    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private CommandeTools commandeTools;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private CommandeServiceImpl commandeServiceImpl;

    private Commande commande;
    private Restaurant restaurant;
    private Paiement paiement;

    @BeforeEach
    void setup() {
        commande = new Commande();
        restaurant = new Restaurant();
        paiement = new Paiement();
    }

    @Test
    void findAllCommandeWithStatut_ReturnsFilteredCommandes() {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(commandeRepository.findAllCommandeWithStatut(any(StatusCommande.class))).thenReturn(Collections.singletonList(commande));
        commande.setRestaurant(restaurant);
        restaurant.setId(1L);

        var result = commandeServiceImpl.findAllCommandeWithStatut(StatusCommande.EN_COURS, "token");

        assertEquals(1, result.size());
    }

    @Test
    void findCommandeById_ReturnsCommande() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));

        var result = commandeServiceImpl.findCommandeById(1L);

        assertNotNull(result);
    }

    @Test
    void findCommandeById_ThrowsExceptionWhenNotFound() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commandeServiceImpl.findCommandeById(1L));
    }

    @Test
    void createCommande_Success() throws PriceException {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(restaurant));
        when(commandeTools.isCorrectPrice(any(Commande.class))).thenReturn(true);
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        var result = commandeServiceImpl.createCommande(commande, "token");

        assertNotNull(result);
    }

    @Test
    void createCommande_ThrowsPriceException() {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(restaurant));
        when(commandeTools.isCorrectPrice(any(Commande.class))).thenReturn(false);

        assertThrows(PriceException.class, () -> commandeServiceImpl.createCommande(commande, "token"));
    }

    @Test
    void deleteCommande_ReturnsTrueWhenDeleted() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));

        var result = commandeServiceImpl.deleteCommande(1L);

        assertTrue(result);
    }

    @Test
    void deleteCommande_ReturnsFalseWhenNotFound() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.empty());

        var result = commandeServiceImpl.deleteCommande(1L);

        assertFalse(result);
    }

    @Test
    void ajoutPaiement_AddsPaiement() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        var result = commandeServiceImpl.ajoutPaiement(commande, paiement);

        assertNotNull(result);
    }

    @Test
    void advanceStatusCommande_AdvancesStatus() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        commande.setStatus(StatusCommande.EN_COURS);
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        var result = commandeServiceImpl.advanceStatusCommande(1L);

        assertEquals(StatusCommande.TERMINEE, result.getStatus());
    }

    @Test
    void advanceStatusCommande_DoesNotAdvanceStatusWhenNotEnCours() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        commande.setStatus(StatusCommande.TERMINEE);

        var result = commandeServiceImpl.advanceStatusCommande(1L);

        assertEquals(StatusCommande.TERMINEE, result.getStatus());
    }

    @Test
    void updateCommandeWithValidId() {
        Long id = 1L;
        Commande updatedCommande = new Commande();
        Commande existingCommande = new Commande();
        when(commandeRepository.findById(id)).thenReturn(Optional.of(existingCommande));
        when(commandeRepository.save(updatedCommande)).thenReturn(updatedCommande);

        var result = commandeServiceImpl.updateCommande(id, updatedCommande);

        assertNotNull(result);
        assertEquals(id, updatedCommande.getId());
    }

    @Test
    void updateCommandeWithInvalidId() {
        Long id = 1L;
        Commande updatedCommande = new Commande();
        when(commandeRepository.findById(id)).thenReturn(Optional.empty());

        var result = commandeServiceImpl.updateCommande(id, updatedCommande);

        assertNull(result);
    }

    @Test
    void updateCommandeWithNullUpdatedCommande() {
        Long id = 1L;
        when(commandeRepository.findById(id)).thenReturn(Optional.of(new Commande()));

        var result = commandeServiceImpl.updateCommande(id, null);

        assertNull(result);
    }
}