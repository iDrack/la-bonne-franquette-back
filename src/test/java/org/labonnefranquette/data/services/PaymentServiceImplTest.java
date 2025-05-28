package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
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
import org.labonnefranquette.data.services.impl.PaymentServiceImpl;
import org.labonnefranquette.data.services.impl.RestaurantServiceImpl;
import org.labonnefranquette.data.utils.PDFTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private RestaurantServiceImpl restaurantService;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setTotalPrice(100);
        order.setPaymentType("AUCUN");
        order.setPayments(Collections.emptyList());
    }

    @Test
    void getAll_ShouldReturnList() {
        List<Payment> payments = List.of(new Payment(), new Payment());
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAll();

        assertSame(payments, result);
        verify(paymentRepository).findAll();
    }

    @Test
    void getById_WhenExists_ShouldReturnPayment() {
        Payment p = new Payment();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(p));

        Payment result = paymentService.getById(1L);

        assertSame(p, result);
        verify(paymentRepository).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrow() {
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.getById(2L));
        assertEquals("Le Paiement n'existe pas.", ex.getMessage());
    }

    @Test
    void create_ShouldLinkPayment() {
        Payment payment = new Payment();
        payment.setPrice(100);
        payment.setType("CB");
        when(orderService.getById(1L)).thenReturn(order);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.create(1L, payment);

        assertSame(payment, result);
        verify(orderService).getById(1L);
        verify(paymentRepository).save(payment);
        verify(orderRepository).save(order);
    }

    @Test
    void getAllByOrder_WhenExists_ShouldReturnPayments() {
        List<Payment> list = List.of(new Payment());
        when(paymentRepository.findByOrderId(1L)).thenReturn(Optional.of(list));

        List<Payment> result = paymentService.getAllByOrder(1L);

        assertSame(list, result);
        verify(paymentRepository).findByOrderId(1L);
    }

    @Test
    void getAllByOrder_WhenNone_ShouldThrow() {
        when(paymentRepository.findByOrderId(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.getAllByOrder(2L));
        assertEquals("Aucun paiement n'éxiste pour cette commande.", ex.getMessage());
    }

    @Test
    void getAllPaymentType_ShouldReturnList() {
        List<PaymentType> types = List.of(new PaymentType(), new PaymentType());
        when(paymentTypeRepository.findAll()).thenReturn(types);

        List<PaymentType> result = paymentService.getAllPaymentType();

        assertSame(types, result);
        verify(paymentTypeRepository).findAll();
    }

}
