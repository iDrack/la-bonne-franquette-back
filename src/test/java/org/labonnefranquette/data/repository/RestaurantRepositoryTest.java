package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void saveAndFindById() {
        // Given
        Restaurant resto = new Restaurant();
        resto.setName("Le Gourmet");
        resto.setMenuVersion(1);
        resto.setIsTVAEnable(true);

        // When
        Restaurant saved = restaurantRepository.save(resto);

        // Then
        Optional<Restaurant> maybe = restaurantRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Restaurant found = maybe.get();
        assertThat(found.getName()).isEqualTo("Le Gourmet");
        assertThat(found.getMenuVersion()).isEqualTo(1);
        assertThat(found.getIsTVAEnable()).isTrue();
    }

    @Test
    void findByNameReturnsCorrectRestaurant() {
        // Given
        Restaurant resto = new Restaurant();
        resto.setName("Pizza Place");
        resto.setMenuVersion(1);
        resto.setIsTVAEnable(false);
        restaurantRepository.save(resto);

        // When
        Restaurant found = restaurantRepository.findByName("Pizza Place");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Pizza Place");
        assertThat(found.getIsTVAEnable()).isFalse();
    }

    @Test
    void existsByNameReturnsTrueIfExists() {
        // Given
        Restaurant resto = new Restaurant();
        resto.setName("Burger King");
        resto.setMenuVersion(2);
        resto.setIsTVAEnable(true);
        restaurantRepository.save(resto);

        // When & Then
        assertThat(restaurantRepository.existsByName("Burger King")).isTrue();
        assertThat(restaurantRepository.existsByName("UnNomQuiNexistePas")).isFalse();
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Restaurant resto = new Restaurant();
        resto.setName("Temporaire");
        resto.setMenuVersion(1);
        resto.setIsTVAEnable(false);
        Restaurant saved = restaurantRepository.save(resto);

        // When
        restaurantRepository.deleteById(saved.getId());

        // Then
        Optional<Restaurant> maybe = restaurantRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }

    @Test
    void updateVersionCarteIncrementsMenuVersion() {
        // Given
        Restaurant resto = new Restaurant();
        resto.setName("Versionnable");
        resto.setMenuVersion(5);
        resto.setIsTVAEnable(true);
        Restaurant saved = restaurantRepository.save(resto);

        // When
        saved.updateVersionCarte();
        restaurantRepository.save(saved);

        // Then
        Restaurant found = restaurantRepository.findByName("Versionnable");
        assertThat(found.getMenuVersion()).isEqualTo(6);
    }
}
