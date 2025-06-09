package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class CategoryTest {

    @Test
    void shouldConstructCategoryAndAccessFields() {
        Collection<Product> products = new ArrayList<>();
        Collection<SubCategory> subCategories = new ArrayList<>();

        Category category = new Category(
                1L,
                "Pizza",
                products,
                "category",
                subCategories
        );

        assertEquals(1L, category.getId());
        assertEquals("Pizza", category.getName());
        assertSame(products, category.getProducts());
        assertEquals("category", category.getCategoryType());
        assertSame(subCategories, category.getSubCategories());
    }

    @Test
    void shouldSetAndGetFields() {
        Category category = new Category();

        Collection<Product> products = new ArrayList<>();
        Collection<SubCategory> subCategories = new ArrayList<>();

        category.setId(2L);
        category.setName("Dessert");
        category.setProducts(products);
        category.setCategoryType("category");
        category.setSubCategories(subCategories);

        assertEquals(2L, category.getId());
        assertEquals("Dessert", category.getName());
        assertSame(products, category.getProducts());
        assertEquals("category", category.getCategoryType());
        assertSame(subCategories, category.getSubCategories());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Collection<Product> products1 = new ArrayList<>();
        Collection<SubCategory> subCategories1 = new ArrayList<>();
        Collection<Product> products2 = new ArrayList<>();
        Collection<SubCategory> subCategories2 = new ArrayList<>();

        Category cat1 = new Category(1L, "Boisson", products1, "category", subCategories1);
        Category cat2 = new Category(1L, "Boisson", products1, "category", subCategories1);
        Category cat3 = new Category(3L, "Snack", products2, "category", subCategories2);

        assertEquals(cat1, cat2);
        assertEquals(cat1.hashCode(), cat2.hashCode());
        assertNotEquals(cat1, cat3);
        assertNotEquals(cat1.hashCode(), cat3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        Category category = new Category(4L, "Entrée", new ArrayList<>(), "category", new ArrayList<>());
        String str = category.toString();
        assertTrue(str.contains("Entrée"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializeCategoryToJson() throws JsonProcessingException {
        Category category = new Category(
                99L,
                "Tacos",
                new ArrayList<>(),
                "category",
                new ArrayList<>()
        );

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(category);

        assertTrue(json.contains("\"id\":99"));
        assertTrue(json.contains("\"name\":\"Tacos\""));
        assertTrue(json.contains("\"categoryType\":\"category\""));
        assertTrue(json.contains("\"subCategories\":[]"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Category category = new Category();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(123L);
        restaurant.setName("Test Resto");

        category.setRestaurant(restaurant);

        assertSame(restaurant, category.getRestaurant());
        assertEquals("Test Resto", category.getRestaurant().getName());
        assertEquals(123L, category.getRestaurant().getId());
    }
}
