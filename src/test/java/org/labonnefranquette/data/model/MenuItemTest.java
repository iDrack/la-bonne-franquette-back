package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.VATRate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class MenuItemTest {

    @Test
    void shouldConstructMenuItemAndAccessFields() {
        Menu menu = new Menu();
        List<Product> products = new ArrayList<>();
        MenuItem menuItem = new MenuItem(1L, true, menu, products);

        assertEquals(1L, menuItem.getId());
        assertTrue(menuItem.isOptional());
        assertSame(menu, menuItem.getMenu());
        assertSame(products, menuItem.getProducts());
    }

    @Test
    void shouldSetAndGetFields() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(2L);
        menuItem.setOptional(false);
        Menu menu = new Menu();
        List<Product> products = new ArrayList<>();
        menuItem.setMenu(menu);
        menuItem.setProducts(products);

        assertEquals(2L, menuItem.getId());
        assertFalse(menuItem.isOptional());
        assertSame(menu, menuItem.getMenu());
        assertSame(products, menuItem.getProducts());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Menu menu = new Menu();
        List<Product> products1 = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();
        MenuItem item1 = new MenuItem(1L, true, menu, products1);
        MenuItem item2 = new MenuItem(1L, true, menu, products1);
        MenuItem item3 = new MenuItem(3L, false, menu, products2);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1, item3);
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void shouldHaveToStringWithIdAndOptional() {
        Menu menu = new Menu();
        MenuItem menuItem = new MenuItem(4L, false, menu, new ArrayList<>());
        String str = menuItem.toString();
        assertTrue(str.contains("4"));
        assertTrue(str.contains("optional"));
    }

    @Test
    void shouldSerializeMenuItemToJson() throws JsonProcessingException {
        Menu menu = new Menu(10L, "Menu Test", new ArrayList<>());
        List<Product> products = new ArrayList<>();
        MenuItem menuItem = new MenuItem(5L, true, menu, products);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(menuItem);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"optional\":true"));
        assertTrue(json.contains("\"products\":[]"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        MenuItem menuItem = new MenuItem();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(100L);
        restaurant.setName("Resto MenuItem");

        menuItem.setRestaurant(restaurant);

        assertSame(restaurant, menuItem.getRestaurant());
        assertEquals("Resto MenuItem", menuItem.getRestaurant().getName());
        assertEquals(100L, menuItem.getRestaurant().getId());
    }

    @Test
    void shouldSetAndGetVATRate() {
        MenuItem menuItem = new MenuItem();
        menuItem.setVATRate(VATRate.NORMAL);
        assertEquals(VATRate.NORMAL, menuItem.getVATRate());
    }

    @Test
    void shouldReturnCorrectVATRateFloatWhenTVAEnabled() {
        MenuItem menuItem = new MenuItem();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        menuItem.setRestaurant(restaurant);
        menuItem.setVATRate(VATRate.NORMAL); // NORMAL = 1.2f par exemple

        assertEquals(1.2f, menuItem.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldReturnAucunVATRateFloatWhenTVADisabled() {
        MenuItem menuItem = new MenuItem();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(false);
        menuItem.setRestaurant(restaurant);
        menuItem.setVATRate(VATRate.NORMAL);

        assertEquals(VATRate.AUCUN.getFloat(), menuItem.getVATRateFloat(), 0.0001f);
    }

    @Test
    void shouldCalculateTotalPriceAccordingToHTAndVATRate() {
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(50);
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        menuItem.setRestaurant(restaurant);
        menuItem.setVATRate(VATRate.NORMAL); // suppose 1.2f

        assertEquals(60, menuItem.getTotalPrice());
    }

    @Test
    void shouldSetAndGetTotalPrice() {
        MenuItem menuItem = new MenuItem();
        menuItem.setTotalPrice(120);
        assertEquals(120, menuItem.getPrice());
    }
}
