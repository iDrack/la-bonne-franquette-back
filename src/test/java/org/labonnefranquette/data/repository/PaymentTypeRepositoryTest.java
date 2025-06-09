package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class PaymentTypeRepositoryTest {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("TestResto PaiementType");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        PaymentType type = new PaymentType();
        type.setName("Carte Bancaire");
        type.setIsEnable(true);
        type.setRestaurant(restaurant);

        // When
        PaymentType saved = paymentTypeRepository.save(type);

        // Then
        Optional<PaymentType> maybe = paymentTypeRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        PaymentType found = maybe.get();
        assertThat(found.getName()).isEqualTo("Carte Bancaire");
        assertThat(found.getIsEnable()).isTrue();
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void findByNameReturnsCorrectType() {
        // Given
        PaymentType type = new PaymentType();
        type.setName("Espèces");
        type.setIsEnable(true);
        type.setRestaurant(restaurant);
        paymentTypeRepository.save(type);

        // When
        PaymentType found = paymentTypeRepository.findByName("Espèces");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Espèces");
        assertThat(found.getIsEnable()).isTrue();
    }

    @Test
    void findAllReturnsAll() {
        // Given
        PaymentType t1 = new PaymentType();
        t1.setName("Chèque");
        t1.setIsEnable(true);
        t1.setRestaurant(restaurant);

        PaymentType t2 = new PaymentType();
        t2.setName("Ticket Restaurant");
        t2.setIsEnable(false);
        t2.setRestaurant(restaurant);

        paymentTypeRepository.save(t1);
        paymentTypeRepository.save(t2);

        // When
        var list = paymentTypeRepository.findAll();

        // Then
        assertThat(list)
                .hasSize(2)
                .extracting(PaymentType::getName)
                .containsExactlyInAnyOrder("Chèque", "Ticket Restaurant");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        PaymentType type = new PaymentType();
        type.setName("À supprimer");
        type.setIsEnable(true);
        type.setRestaurant(restaurant);
        PaymentType saved = paymentTypeRepository.save(type);

        // When
        paymentTypeRepository.deleteById(saved.getId());

        // Then
        Optional<PaymentType> maybe = paymentTypeRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
