package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;
    private Order order;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Resto Paiement");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);

        order = new Order();
        order.setNumber((short)1);
        order.setCreationDate(new Date());
        order.setStatus(OrderStatus.EN_COURS);
        order.setDineIn(true);
        order.setTotalItems(1);
        order.setTotalPrice(1200);
        order.setRestaurant(restaurant);
        order.setPaid(false);
        orderRepository.save(order);

        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        Payment payment = new Payment();
        payment.setType("CB");
        payment.setPrice(1200);
        payment.setOrder(order);
        payment.setRestaurant(restaurant);

        // When
        Payment saved = paymentRepository.save(payment);

        // Then
        Optional<Payment> maybe = paymentRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Payment found = maybe.get();
        assertThat(found.getType()).isEqualTo("CB");
        assertThat(found.getPrice()).isEqualTo(1200);
        assertThat(found.getOrder().getId()).isEqualTo(order.getId());
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void findByOrderIdReturnsPaymentsForOrder() {
        // Given
        Payment p1 = new Payment();
        p1.setType("Espèces");
        p1.setPrice(500);
        p1.setOrder(order);
        p1.setRestaurant(restaurant);

        Payment p2 = new Payment();
        p2.setType("CB");
        p2.setPrice(700);
        p2.setOrder(order);
        p2.setRestaurant(restaurant);

        paymentRepository.saveAll(List.of(p1, p2));

        // When
        Optional<List<Payment>> maybePayments = paymentRepository.findByOrderId(order.getId());

        // Then
        assertThat(maybePayments).isPresent();
        List<Payment> payments = maybePayments.get();
        assertThat(payments).hasSize(2)
                .extracting(Payment::getType)
                .containsExactlyInAnyOrder("Espèces", "CB");
    }

    @Test
    void findByOrderIdReturnsEmptyIfNone() {
        // When
        Optional<List<Payment>> maybePayments = paymentRepository.findByOrderId(-999L);

        // Then
        assertThat(maybePayments).isPresent();
        assertThat(maybePayments.get()).isEmpty();
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Payment payment = new Payment();
        payment.setType("Chèque");
        payment.setPrice(250);
        payment.setOrder(order);
        payment.setRestaurant(restaurant);
        Payment saved = paymentRepository.save(payment);

        // When
        paymentRepository.deleteById(saved.getId());

        // Then
        Optional<Payment> maybe = paymentRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
