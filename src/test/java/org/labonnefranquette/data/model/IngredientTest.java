package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class IngredientTest {

    @Test
    void shouldConstructIngredientAndAccessFields() {
        Set<Product> products = new HashSet<>();
        Ingredient ingredient = new Ingredient(1L, "Mozzarella", products);

        assertEquals(1L, ingredient.getId());
        assertEquals("Mozzarella", ingredient.getName());
        assertSame(products, ingredient.getProducts());
    }

    @Test
    void shouldSetAndGetFields() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        ingredient.setName("Jambon");
        Set<Product> products = new HashSet<>();
        ingredient.setProducts(products);

        assertEquals(2L, ingredient.getId());
        assertEquals("Jambon", ingredient.getName());
        assertSame(products, ingredient.getProducts());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Set<Product> products1 = new HashSet<>();
        Set<Product> products2 = new HashSet<>();
        Ingredient ing1 = new Ingredient(1L, "Chorizo", products1);
        Ingredient ing2 = new Ingredient(1L, "Chorizo", products1);
        Ingredient ing3 = new Ingredient(3L, "Basilic", products2);

        assertEquals(ing1, ing2);
        assertEquals(ing1.hashCode(), ing2.hashCode());
        assertNotEquals(ing1, ing3);
        assertNotEquals(ing1.hashCode(), ing3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        Ingredient ingredient = new Ingredient(4L, "Aubergine", new HashSet<>());
        String str = ingredient.toString();
        assertTrue(str.contains("Aubergine"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializeIngredientToJson() throws JsonProcessingException {
        Ingredient ingredient = new Ingredient(5L, "Tomate", new HashSet<>());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ingredient);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"name\":\"Tomate\""));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Ingredient ingredient = new Ingredient();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(42L);
        restaurant.setName("Pasta Palace");

        ingredient.setRestaurant(restaurant);

        assertSame(restaurant, ingredient.getRestaurant());
        assertEquals("Pasta Palace", ingredient.getRestaurant().getName());
        assertEquals(42L, ingredient.getRestaurant().getId());
    }
}
