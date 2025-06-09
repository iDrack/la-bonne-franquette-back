package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.VATRate;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class AddonTest {

    @Test
    void shouldConstructAddonAndAccessFields() {
        Set<Product> products = new HashSet<>();
        Addon addon = new Addon(1L, "Fromage", products);

        assertEquals(1L, addon.getId());
        assertEquals("Fromage", addon.getName());
        assertSame(products, addon.getProducts());
    }

    @Test
    void shouldSetAndGetFields() {
        Addon addon = new Addon();
        addon.setId(2L);
        addon.setName("Bacon");

        Set<Product> products = new HashSet<>();
        addon.setProducts(products);

        assertEquals(2L, addon.getId());
        assertEquals("Bacon", addon.getName());
        assertSame(products, addon.getProducts());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Set<Product> products1 = new HashSet<>();
        Set<Product> products2 = new HashSet<>();

        Addon addon1 = new Addon(1L, "Sauce", products1);
        Addon addon2 = new Addon(1L, "Sauce", products1);
        Addon addon3 = new Addon(2L, "Ketchup", products2);

        assertEquals(addon1, addon2);
        assertNotEquals(addon1, addon3);

        assertEquals(addon1.hashCode(), addon2.hashCode());
        assertNotEquals(addon1.hashCode(), addon3.hashCode());
    }

    @Test
    void shouldHaveToStringWithName() {
        Addon addon = new Addon(3L, "Oeuf", new HashSet<>());
        String str = addon.toString();
        assertTrue(str.contains("Oeuf"));
        assertTrue(str.contains("3"));
    }

    @Test
    void shouldSerializeAddonToJson() throws JsonProcessingException {
        Addon addon = new Addon(1L, "Sauce", null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addon);

        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"name\":\"Sauce\""));
    }

    @Test
    void shouldReturnCorrectVATRateFloatWhenTVAEnabled() {
        Addon addon = new Addon();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        addon.setRestaurant(restaurant);
        addon.setVATRate(VATRate.NORMAL);

        assertEquals(1.2f, addon.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldReturnAucunVATRateFloatWhenTVADisabled() {
        Addon addon = new Addon();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(false);
        addon.setRestaurant(restaurant);
        addon.setVATRate(VATRate.NORMAL);

        assertEquals(VATRate.AUCUN.getFloat(), addon.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldCalculateTotalPriceAccordingToHTAndVATRate() {
        Addon addon = new Addon();
        addon.setPrice(100);
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        addon.setRestaurant(restaurant);
        addon.setVATRate(VATRate.NORMAL);

        assertEquals(120, addon.getTotalPrice());
    }

    @Test
    void shouldSetAndGetTotalPrice() {
        Addon addon = new Addon();
        addon.setTotalPrice(250);
        assertEquals(250, addon.getPrice());
    }
}
