package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("TestResto Order");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    private Order createOrder(short number, java.util.Date creationDate, OrderStatus status, boolean paid, int totalPrice) {
        Order order = new Order();
        order.setNumber(number);
        order.setCreationDate(creationDate);
        order.setStatus(status);
        order.setDineIn(true);
        order.setTotalItems(1);
        order.setTotalPrice(totalPrice);
        order.setRestaurant(restaurant);
        order.setPaid(paid);
        return order;
    }

    @Test
    void saveAndFindById() {
        // Given
        Order order = createOrder((short)12, new java.util.Date(), OrderStatus.EN_COURS, false, 1200);

        // When
        Order saved = orderRepository.save(order);

        // Then
        Optional<Order> maybe = orderRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Order found = maybe.get();
        assertThat(found.getNumber()).isEqualTo((short)12);
        assertThat(found.getStatus()).isEqualTo(OrderStatus.EN_COURS);
        assertThat(found.getTotalPrice()).isEqualTo(1200);
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(found.isPaid()).isFalse();
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Order o1 = createOrder((short)1, new java.util.Date(), OrderStatus.EN_COURS, false, 800);
        Order o2 = createOrder((short)2, new java.util.Date(), OrderStatus.TERMINEE, true, 1500);
        orderRepository.save(o1);
        orderRepository.save(o2);

        // When
        List<Order> all = orderRepository.findAll();

        // Then
        assertThat(all).hasSize(2)
                .extracting(Order::getNumber)
                .containsExactlyInAnyOrder((short)1, (short)2);
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Order order = createOrder((short)99, new java.util.Date(), OrderStatus.ANNULEE, false, 50);
        Order saved = orderRepository.save(order);

        // When
        orderRepository.deleteById(saved.getId());

        // Then
        Optional<Order> maybe = orderRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }

    @Test
    void findAllByStatusReturnsOnlyMatchingStatus() {
        // Given
        Order o1 = createOrder((short)3, new java.util.Date(), OrderStatus.EN_COURS, false, 1100);
        Order o2 = createOrder((short)4, new java.util.Date(), OrderStatus.TERMINEE, true, 1200);
        Order o3 = createOrder((short)5, new java.util.Date(), OrderStatus.EN_COURS, false, 900);
        orderRepository.saveAll(List.of(o1, o2, o3));

        // When
        List<Order> EN_COURSOrders = orderRepository.findAllByStatus(OrderStatus.EN_COURS);

        // Then
        assertThat(EN_COURSOrders).hasSize(2)
                .allMatch(o -> o.getStatus() == OrderStatus.EN_COURS);
    }

    @Test
    void findAllByRestaurantReturnsOnlyFromThatRestaurant() {
        // Given
        Order o1 = createOrder((short)11, new java.util.Date(), OrderStatus.EN_COURS, false, 1300);
        orderRepository.save(o1);

        // Autre resto
        Restaurant otherRestaurant = new Restaurant();
        otherRestaurant.setName("Autre Resto");
        otherRestaurant.setIsTVAEnable(true);
        em.persist(otherRestaurant);
        em.flush();

        Order o2 = new Order();
        o2.setNumber((short)12);
        o2.setCreationDate(new java.util.Date());
        o2.setStatus(OrderStatus.TERMINEE);
        o2.setDineIn(true);
        o2.setTotalItems(1);
        o2.setTotalPrice(1700);
        o2.setRestaurant(otherRestaurant);
        o2.setPaid(true);
        orderRepository.save(o2);

        // When
        List<Order> results = orderRepository.findAllByRestaurant(restaurant.getId());

        // Then
        assertThat(results)
                .hasSize(1)
                .allMatch(o -> o.getRestaurant().getId().equals(restaurant.getId()));
    }
}
