package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.VATRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Le Test Resto");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        MenuItem menuItem = new MenuItem();
        menuItem.setOptional(false);
        menuItem.setPrice(2000);
        menuItem.setVATRate(VATRate.NORMAL);
        menuItem.setRestaurant(restaurant);

        // When
        MenuItem saved = menuItemRepository.save(menuItem);

        // Then
        Optional<MenuItem> maybe = menuItemRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        MenuItem found = maybe.get();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getPrice()).isEqualTo(saved.getPrice());
        assertThat(found.getRestaurant().getId()).isEqualTo(saved.getRestaurant().getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        MenuItem m1 = new MenuItem();
        m1.setPrice(2000);
        m1.setOptional(true);
        m1.setVATRate(VATRate.AUCUN);
        m1.setRestaurant(restaurant);
        menuItemRepository.save(m1);

        MenuItem m2 = new MenuItem();
        m2.setOptional(false);
        m2.setPrice(3000);
        m2.setVATRate(VATRate.NORMAL);
        m2.setRestaurant(restaurant);
        menuItemRepository.save(m2);

        MenuItem m3 = new MenuItem();
        m3.setOptional(true);
        m3.setPrice(7000);
        m3.setVATRate(VATRate.INTERMEDIAIRE);
        m3.setRestaurant(restaurant);
        menuItemRepository.save(m3);

        // When
        List<MenuItem> liste = menuItemRepository.findAll();

        // Then
        assertThat(liste)
                .hasSize(3)
                .extracting(MenuItem::getId)
                .containsExactlyInAnyOrder(m1.getId(), m2.getId(), m3.getId());
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        MenuItem toRemove = new MenuItem();
        toRemove.setPrice(2500);
        toRemove.setRestaurant(restaurant);
        toRemove.setVATRate(VATRate.NORMAL);
        MenuItem saved = menuItemRepository.save(toRemove);

        // When
        menuItemRepository.deleteById(saved.getId());

        // Then
        Optional<MenuItem> maybe = menuItemRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
