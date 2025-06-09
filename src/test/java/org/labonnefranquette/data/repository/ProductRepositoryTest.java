package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.*;
import org.labonnefranquette.data.model.enums.VATRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Resto Produit");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        Product product = new Product();
        product.setName("Cheeseburger");
        product.setPrice(900);
        product.setVATRate(VATRate.NORMAL);
        product.setRestaurant(restaurant);

        // When
        Product saved = productRepository.save(product);

        // Then
        Optional<Product> maybe = productRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Product found = maybe.get();
        assertThat(found.getName()).isEqualTo("Cheeseburger");
        assertThat(found.getPrice()).isEqualTo(900);
        assertThat(found.getVATRate()).isEqualTo(VATRate.NORMAL);
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Product p1 = new Product();
        p1.setName("Pizza");
        p1.setPrice(1100);
        p1.setVATRate(VATRate.NORMAL);
        p1.setRestaurant(restaurant);

        Product p2 = new Product();
        p2.setName("Hot Dog");
        p2.setPrice(800);
        p2.setVATRate(VATRate.NORMAL);
        p2.setRestaurant(restaurant);

        productRepository.saveAll(List.of(p1, p2));

        // When
        List<Product> list = productRepository.findAll();

        // Then
        assertThat(list)
                .hasSize(2)
                .extracting(Product::getName)
                .containsExactlyInAnyOrder("Pizza", "Hot Dog");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Product product = new Product();
        product.setName("À Supprimer");
        product.setPrice(650);
        product.setVATRate(VATRate.NORMAL);
        product.setRestaurant(restaurant);
        Product saved = productRepository.save(product);

        // When
        productRepository.deleteById(saved.getId());

        // Then
        Optional<Product> maybe = productRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }

    @Test
    void saveProductWithRelations() {
        // Given : create and persist related entities
        Category category = new Category();
        category.setName("Fast Food");
        category.setRestaurant(restaurant);
        em.persist(category);

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Steak");
        ingredient.setRestaurant(restaurant);
        em.persist(ingredient);

        Addon addon = new Addon();
        addon.setName("Cheddar");
        addon.setVATRate(VATRate.NORMAL);
        addon.setPrice(200);
        addon.setRestaurant(restaurant);
        em.persist(addon);

        em.flush();

        Product product = new Product();
        product.setName("Burger Gourmet");
        product.setPrice(1400);
        product.setVATRate(VATRate.NORMAL);
        product.setRestaurant(restaurant);

        product.setCategories(Set.of(category));
        product.setIngredients(Set.of(ingredient));
        product.setAddons(Set.of(addon));

        // When
        Product saved = productRepository.save(product);

        // Then
        Optional<Product> maybe = productRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Product found = maybe.get();

        assertThat(found.getCategories()).extracting(Category::getName).contains("Fast Food");
        assertThat(found.getIngredients()).extracting(Ingredient::getName).contains("Steak");
        assertThat(found.getAddons()).extracting(Addon::getName).contains("Cheddar");
    }
}
