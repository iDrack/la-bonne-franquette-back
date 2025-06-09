package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
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
        Category category = new Category();
        category.setName("Sauces");
        category.setRestaurant(restaurant);

        // When
        Category saved = categoryRepository.save(category);

        // Then
        Optional<Category> maybe = categoryRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Category found = maybe.get();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getRestaurant().getId()).isEqualTo(saved.getRestaurant().getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Category c1 = new Category();
        c1.setName("Sauces");
        c1.setRestaurant(restaurant);
        categoryRepository.save(c1);

        Category c2 = new Category();
        c2.setName("Desserts");
        c2.setRestaurant(restaurant);
        categoryRepository.save(c2);

        Category c3 = new Category();
        c3.setName("Entrées");
        c3.setRestaurant(restaurant);
        categoryRepository.save(c3);

        // When
        List<Category> liste = categoryRepository.findAll();

        // Then
        assertThat(liste)
                .hasSize(3)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Sauces", "Desserts", "Entrées");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Category toRemove = new Category();
        toRemove.setName("Plats");
        toRemove.setRestaurant(restaurant);
        Category saved = categoryRepository.save(toRemove);

        // When
        categoryRepository.deleteById(saved.getId());

        // Then
        Optional<Category> maybe = categoryRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
