package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.repository.OrderRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.utils.OrderTools;
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
    private OrderRepository commandeRepository;
    @Mock
    private OrderTools commandeTools;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private OrderServiceImpl commandeServiceImpl;

    private Order commande;
    private Restaurant restaurant;
    private Payment paiement;

    @BeforeEach
    void setup() {
        commande = new Order();
        restaurant = new Restaurant();
        paiement = new Payment();
    }

    @Test
    void findAllCommandeWithStatut_ReturnsFilteredCommandes() {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(commandeRepository.findAllByStatus(any(OrderStatus.class))).thenReturn(Collections.singletonList(commande));
        commande.setRestaurant(restaurant);
        restaurant.setId(1L);

        var result = commandeServiceImpl.getAllByStatus(OrderStatus.EN_COURS, "token");

        assertEquals(1, result.size());
    }

    @Test
    void findCommandeById_ReturnsCommande() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));

        var result = commandeServiceImpl.getById(1L);

        assertNotNull(result);
    }

    @Test
    void findCommandeById_ThrowsExceptionWhenNotFound() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commandeServiceImpl.getById(1L));
    }

    @Test
    void createCommande_Success() throws PriceException {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(restaurant));
        when(commandeTools.checkPrice(any(Order.class))).thenReturn(true);
        when(commandeRepository.save(any(Order.class))).thenReturn(commande);

        var result = commandeServiceImpl.create(commande, "token");

        assertNotNull(result);
    }

    @Test
    void createCommande_ThrowsPriceException() {
        when(jwtUtil.extractRestaurantId(anyString())).thenReturn(1L);
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(restaurant));
        when(commandeTools.checkPrice(any(Order.class))).thenReturn(false);

        assertThrows(PriceException.class, () -> commandeServiceImpl.create(commande, "token"));
    }

    @Test
    void deleteCommande_ReturnsTrueWhenDeleted() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));

        var result = commandeServiceImpl.delete(1L);

        assertTrue(result);
    }

    @Test
    void deleteCommande_ReturnsFalseWhenNotFound() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.empty());

        var result = commandeServiceImpl.delete(1L);

        assertFalse(result);
    }

    @Test
    void ajoutPaiement_AddsPaiement() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Order.class))).thenReturn(commande);

        var result = commandeServiceImpl.addPayment(commande, paiement);

        assertNotNull(result);
    }

    @Test
    void advanceStatusCommande_AdvancesStatus() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        commande.setStatus(OrderStatus.EN_COURS);
        when(commandeRepository.save(any(Order.class))).thenReturn(commande);

        var result = commandeServiceImpl.updateStatus(1L);

        assertEquals(OrderStatus.TERMINEE, result.getStatus());
    }

    @Test
    void advanceStatusCommande_DoesNotAdvanceStatusWhenNotEnCours() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        commande.setStatus(OrderStatus.TERMINEE);

        var result = commandeServiceImpl.updateStatus(1L);

        assertEquals(OrderStatus.TERMINEE, result.getStatus());
    }
}