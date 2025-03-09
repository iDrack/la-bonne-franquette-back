package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class IngredientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        Ingredient ingredient = new Ingredient();
        ingredient.setNom("Tomato");
        entityManager.persistAndFlush(ingredient);
    }

    @Test
    public void findAll_returnsAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(1);
        assertThat(ingredients.get(0).getNom()).isEqualTo("Tomato");
    }

    @Test
    public void findById_returnsCorrectIngredient() {
        Ingredient ingredientTest = new Ingredient();
        ingredientTest.setNom("test");
        entityManager.persistAndFlush(ingredientTest);
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientTest.getId());
        assertThat(ingredient.isPresent()).isTrue();
        assertThat(ingredient.get().getNom()).isEqualTo("test");
    }

    @Test
    public void findById_returnsEmptyForNonExistingId() {
        Optional<Ingredient> ingredient = ingredientRepository.findById(999L);
        assertThat(ingredient.isPresent()).isFalse();
    }

    @Test
    public void save_savesAndReturnsIngredient() {
        Ingredient newIngredient = new Ingredient();
        newIngredient.setNom("Cheese");
        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        assertThat(savedIngredient).isNotNull();
        assertThat(savedIngredient.getNom()).isEqualTo("Cheese");
    }

    @Test
    public void deleteById_deletesIngredient() {
        ingredientRepository.deleteById(1L);
        Optional<Ingredient> ingredient = ingredientRepository.findById(1L);
        assertThat(ingredient.isPresent()).isFalse();
    }
}