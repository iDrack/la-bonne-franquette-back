package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.repository.OrderRepository;
import org.labonnefranquette.data.repository.PaymentRepository;
import org.labonnefranquette.data.repository.PaymentTypeRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.OrderService;
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
    private PaymentRepository paiementRepository;

    @Mock
    private OrderService commandeService;

    @Mock
    private PaymentTypeRepository paiementTypeRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private OrderRepository commandeRepository;

    @InjectMocks
    private PaymentServiceImpl paiementService;

    @Test
    void shouldReturnAllPaiements() {
        List<Payment> paiements = new ArrayList<>();
        when(paiementRepository.findAll()).thenReturn(paiements);

        List<Payment> result = paiementService.getAll();

        assertEquals(paiements, result);
    }

    @Test
    void shouldReturnPaiementById() {
        Payment paiement = new Payment();
        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));

        Payment result = paiementService.getById(1L);

        assertEquals(paiement, result);
    }

    @Test
    void shouldThrowExceptionWhenPaiementNotFoundById() {
        when(paiementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paiementService.getById(1L));
    }

    @Test
    void shouldCreatePaiement() {
        Order commande = new Order();
        Payment paiement = new Payment();
        when(commandeService.getById(1L)).thenReturn(commande);
        when(paiementRepository.save(paiement)).thenReturn(paiement);

        Payment result = paiementService.create(1L, paiement);

        assertEquals(paiement, result);
        verify(paiementRepository).save(paiement);
    }

    @Test
    void shouldReturnPaiementsByCommande() {
        List<Payment> paiements = new ArrayList<>();
        when(paiementRepository.findByOrderId(1L)).thenReturn(Optional.of(paiements));

        List<Payment> result = paiementService.getAllByOrder(1L);

        assertEquals(paiements, result);
    }

    @Test
    void shouldThrowExceptionWhenNoPaiementsForCommande() {
        when(paiementRepository.findByOrderId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paiementService.getAllByOrder(1L));
    }

    @Test
    void shouldReturnAllPaiementTypes() {
        List<PaymentType> paiementTypes = new ArrayList<>();
        when(paiementTypeRepository.findAll()).thenReturn(paiementTypes);

        List<PaymentType> result = paiementService.getAllPaymentType();

        assertEquals(paiementTypes, result);
    }
}