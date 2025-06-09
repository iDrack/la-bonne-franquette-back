package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;
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
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Tomate");
        ingredient.setRestaurant(restaurant);

        // When
        Ingredient saved = ingredientRepository.save(ingredient);

        // Then
        Optional<Ingredient> maybe = ingredientRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Ingredient found = maybe.get();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getRestaurant().getId()).isEqualTo(saved.getRestaurant().getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Ingredient i1 = new Ingredient();
        i1.setName("Frite");
        i1.setRestaurant(restaurant);
        ingredientRepository.save(i1);

        Ingredient i2 = new Ingredient();
        i2.setName("Riz");
        i2.setRestaurant(restaurant);
        ingredientRepository.save(i2);

        Ingredient i3 = new Ingredient();
        i3.setName("Fanta");
        i3.setRestaurant(restaurant);
        ingredientRepository.save(i3);

        // When
        List<Ingredient> liste = ingredientRepository.findAll();

        // Then
        assertThat(liste)
                .hasSize(3)
                .extracting(Ingredient::getName)
                .containsExactlyInAnyOrder("Frite", "Riz", "Fanta");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Ingredient toRemove = new Ingredient();
        toRemove.setName("Chips");
        toRemove.setRestaurant(restaurant);
        Ingredient saved = ingredientRepository.save(toRemove);

        // When
        ingredientRepository.deleteById(saved.getId());

        // Then
        Optional<Ingredient> maybe = ingredientRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
