package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class RestaurantTest {

    @Test
    void shouldConstructRestaurantAndAccessFields() {
        Restaurant restaurant = new Restaurant(
                1L,
                "Chez Maurice",
                4,
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        assertEquals(1L, restaurant.getId());
        assertEquals("Chez Maurice", restaurant.getName());
        assertEquals(4, restaurant.getMenuVersion());
        assertTrue(restaurant.getIsTVAEnable());
        assertNotNull(restaurant.getEmployees());
        assertNotNull(restaurant.getCategories());
        assertNotNull(restaurant.getOrders());
        assertNotNull(restaurant.getAddons());
        assertNotNull(restaurant.getIngredients());
        assertNotNull(restaurant.getMenus());
        assertNotNull(restaurant.getMenuItems());
        assertNotNull(restaurant.getPayments());
        assertNotNull(restaurant.getPaymentTypes());
        assertNotNull(restaurant.getProducts());
        assertNotNull(restaurant.getSubCategories());
    }

    @Test
    void shouldSetAndGetFields() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        restaurant.setName("Le Gourmet");
        restaurant.setMenuVersion(7);
        restaurant.setIsTVAEnable(true);

        assertEquals(2L, restaurant.getId());
        assertEquals("Le Gourmet", restaurant.getName());
        assertEquals(7, restaurant.getMenuVersion());
        assertTrue(restaurant.getIsTVAEnable());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Restaurant r1 = new Restaurant(3L, "L'Auberge", 2, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Restaurant r2 = new Restaurant(3L, "L'Auberge", 2, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Restaurant r3 = new Restaurant(8L, "Le Bistrot", 1, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        Restaurant restaurant = new Restaurant(4L, "L'Estaminet", 1, false, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String str = restaurant.toString();
        assertTrue(str.contains("L'Estaminet"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializeRestaurantToJson() throws JsonProcessingException {
        Restaurant restaurant = new Restaurant(5L, "La Cantine", 3, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(restaurant);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"name\":\"La Cantine\""));
        assertTrue(json.contains("\"menuVersion\":3"));
        assertTrue(json.contains("\"isTVAEnable\":true"));
        assertTrue(json.contains("\"employees\":[]"));
        assertTrue(json.contains("\"categories\":[]"));
        assertTrue(json.contains("\"orders\":[]"));
        assertTrue(json.contains("\"addons\":[]"));
        assertTrue(json.contains("\"ingredients\":[]"));
        assertTrue(json.contains("\"menus\":[]"));
        assertTrue(json.contains("\"menuItems\":[]"));
        assertTrue(json.contains("\"payments\":[]"));
        assertTrue(json.contains("\"paymentTypes\":[]"));
        assertTrue(json.contains("\"products\":[]"));
        assertTrue(json.contains("\"subCategories\":[]"));
    }

    @Test
    void shouldUpdateVersionCarte() {
        Restaurant restaurant = new Restaurant();
        restaurant.setMenuVersion(10);
        restaurant.updateVersionCarte();
        assertEquals(11, restaurant.getMenuVersion());
    }
}
