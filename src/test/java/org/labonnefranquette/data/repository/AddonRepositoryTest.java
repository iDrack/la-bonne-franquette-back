package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.VATRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Tag("integration")
class AddonRepositoryTest {

    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Mon Resto");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        Addon addon = new Addon();
        addon.setName("Sauce Fromage");
        addon.setVATRate(VATRate.NORMAL);
        addon.setPrice(250);
        addon.setRestaurant(restaurant);

        // When
        Addon saved = addonRepository.save(addon);

        // Then
        Optional<Addon> maybe = addonRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Addon found = maybe.get();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getVATRate()).isEqualTo(saved.getVATRate());
        assertThat(found.getPrice()).isEqualTo(250);
        assertThat(found.getTotalPrice()).isEqualTo(saved.getTotalPrice());
        assertThat(found.getRestaurant().getId()).isEqualTo(saved.getRestaurant().getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Addon a1 = new Addon();
        a1.setName("Extra Ketchup");
        a1.setVATRate(VATRate.NORMAL);
        a1.setPrice(100);
        a1.setRestaurant(restaurant);
        addonRepository.save(a1);

        Addon a2 = new Addon();
        a2.setName("Bacon Croquant");
        a2.setVATRate(VATRate.AUCUN);
        a2.setPrice(300);
        a2.setRestaurant(restaurant);
        addonRepository.save(a2);

        Addon a3 = new Addon();
        a3.setName("Nuggets");
        a3.setVATRate(VATRate.INTERMEDIAIRE);
        a3.setPrice(200);
        a3.setRestaurant(restaurant);
        addonRepository.save(a3);

        // When
        List<Addon> liste = addonRepository.findAll();

        // Then
        assertThat(liste)
                .hasSize(3)
                .extracting(Addon::getName)
                .containsExactlyInAnyOrder("Extra Ketchup", "Bacon Croquant", "Nuggets");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Addon toRemove = new Addon();
        toRemove.setName("Oignons Frits");
        toRemove.setVATRate(VATRate.NORMAL);
        toRemove.setPrice(150);
        toRemove.setRestaurant(restaurant);
        Addon saved = addonRepository.save(toRemove);

        // When
        addonRepository.deleteById(saved.getId());

        // Then
        Optional<Addon> maybe = addonRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
