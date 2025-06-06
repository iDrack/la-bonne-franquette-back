package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.VATRate;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class ProductTest {

    @Test
    void shouldConstructProductAndAccessFields() {
        Collection<Category> categories = new ArrayList<>();
        Collection<Ingredient> ingredients = new ArrayList<>();
        Collection<Addon> addons = new ArrayList<>();
        Product product = new Product(1L, "Burger", categories, ingredients, addons);

        assertEquals(1L, product.getId());
        assertEquals("Burger", product.getName());
        assertSame(categories, product.getCategories());
        assertSame(ingredients, product.getIngredients());
        assertSame(addons, product.getAddons());
    }

    @Test
    void shouldSetAndGetFields() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Pizza");
        Collection<Category> categories = new ArrayList<>();
        Collection<Ingredient> ingredients = new ArrayList<>();
        Collection<Addon> addons = new ArrayList<>();
        product.setCategories(categories);
        product.setIngredients(ingredients);
        product.setAddons(addons);

        assertEquals(2L, product.getId());
        assertEquals("Pizza", product.getName());
        assertSame(categories, product.getCategories());
        assertSame(ingredients, product.getIngredients());
        assertSame(addons, product.getAddons());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Collection<Category> categories1 = new ArrayList<>();
        Collection<Ingredient> ingredients1 = new ArrayList<>();
        Collection<Addon> addons1 = new ArrayList<>();

        Collection<Category> categories2 = new ArrayList<>();
        Collection<Ingredient> ingredients2 = new ArrayList<>();
        Collection<Addon> addons2 = new ArrayList<>();

        Product p1 = new Product(1L, "Wrap", categories1, ingredients1, addons1);
        Product p2 = new Product(1L, "Wrap", categories1, ingredients1, addons1);
        Product p3 = new Product(3L, "Kebab", categories2, ingredients2, addons2);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        Product product = new Product(4L, "Frites", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String str = product.toString();
        assertTrue(str.contains("Frites"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializeProductToJson() throws JsonProcessingException {
        Product product = new Product(5L, "Salade", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"name\":\"Salade\""));
        assertTrue(json.contains("\"categories\":[]"));
        assertTrue(json.contains("\"ingredients\":[]"));
        assertTrue(json.contains("\"addons\":[]"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Product product = new Product();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(200L);
        restaurant.setName("Le Producteur");

        product.setRestaurant(restaurant);

        assertSame(restaurant, product.getRestaurant());
        assertEquals("Le Producteur", product.getRestaurant().getName());
        assertEquals(200L, product.getRestaurant().getId());
    }

    @Test
    void shouldSetAndGetVATRate() {
        Product product = new Product();
        product.setVATRate(VATRate.NORMAL);
        assertEquals(VATRate.NORMAL, product.getVATRate());
    }

    @Test
    void shouldReturnCorrectVATRateFloatWhenTVAEnabled() {
        Product product = new Product();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        product.setRestaurant(restaurant);
        product.setVATRate(VATRate.NORMAL); // NORMAL = 1.2f par exemple

        assertEquals(1.2f, product.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldReturnAucunVATRateFloatWhenTVADisabled() {
        Product product = new Product();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(false);
        product.setRestaurant(restaurant);
        product.setVATRate(VATRate.NORMAL);

        assertEquals(VATRate.AUCUN.getFloat(), product.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldCalculateTotalPriceAccordingToHTAndVATRate() {
        Product product = new Product();
        product.setPrice(80);
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        product.setRestaurant(restaurant);
        product.setVATRate(VATRate.NORMAL); // suppose 1.2f

        assertEquals(96, product.getTotalPrice());
    }

    @Test
    void shouldSetAndGetTotalPrice() {
        Product product = new Product();
        product.setTotalPrice(33);
        assertEquals(33, product.getPrice());
    }
}
