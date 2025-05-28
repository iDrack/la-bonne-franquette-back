package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.TauxTVA;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class MenuTest {

    @Test
    void shouldConstructMenuAndAccessFields() {
        Collection<MenuItem> menuItems = new ArrayList<>();
        Menu menu = new Menu(1L, "Formule Midi", menuItems);

        assertEquals(1L, menu.getId());
        assertEquals("Formule Midi", menu.getName());
        assertSame(menuItems, menu.getMenuItems());
    }

    @Test
    void shouldSetAndGetFields() {
        Menu menu = new Menu();
        menu.setId(2L);
        menu.setName("Formule Soir");
        Collection<MenuItem> menuItems = new ArrayList<>();
        menu.setMenuItems(menuItems);

        assertEquals(2L, menu.getId());
        assertEquals("Formule Soir", menu.getName());
        assertSame(menuItems, menu.getMenuItems());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Collection<MenuItem> items1 = new ArrayList<>();
        Collection<MenuItem> items2 = new ArrayList<>();
        Menu menu1 = new Menu(1L, "Menu Enfant", items1);
        Menu menu2 = new Menu(1L, "Menu Enfant", items1);
        Menu menu3 = new Menu(3L, "Menu Gourmand", items2);

        assertEquals(menu1, menu2);
        assertEquals(menu1.hashCode(), menu2.hashCode());
        assertNotEquals(menu1, menu3);
        assertNotEquals(menu1.hashCode(), menu3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        Menu menu = new Menu(4L, "Menu Végé", new ArrayList<>());
        String str = menu.toString();
        assertTrue(str.contains("Menu Végé"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializeMenuToJson() throws JsonProcessingException {
        Menu menu = new Menu(5L, "Menu Pizza", new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(menu);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"name\":\"Menu Pizza\""));
        assertTrue(json.contains("\"menuItems\":[]"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(55L);
        restaurant.setName("RestoTest");

        menu.setRestaurant(restaurant);

        assertSame(restaurant, menu.getRestaurant());
        assertEquals("RestoTest", menu.getRestaurant().getName());
        assertEquals(55L, menu.getRestaurant().getId());
    }

    @Test
    void shouldSetAndGetTauxTVA() {
        Menu menu = new Menu();
        menu.setTauxTVA(TauxTVA.NORMAL);
        assertEquals(TauxTVA.NORMAL, menu.getTauxTVA());
    }

    @Test
    void shouldReturnCorrectTauxTVAFloatWhenTVAEnabled() {
        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        menu.setRestaurant(restaurant);
        menu.setTauxTVA(TauxTVA.NORMAL); // NORMAL = 1.2f par exemple

        assertEquals(1.2f, menu.getTauxTVAFloat(), 0.0001f);
    }

    @Test
    void shouldReturnAucunTauxTVAFloatWhenTVADisabled() {
        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(false);
        menu.setRestaurant(restaurant);
        menu.setTauxTVA(TauxTVA.NORMAL);

        assertEquals(TauxTVA.AUCUN.getFloat(), menu.getTauxTVAFloat(), 0.0001f);
    }

    @Test
    void shouldCalculateTotalPriceAccordingToHTAndTauxTVA() {
        Menu menu = new Menu();
        menu.setPrixHT(150);
        Restaurant restaurant = new Restaurant();
        restaurant.setIsTVAEnable(true);
        menu.setRestaurant(restaurant);
        menu.setTauxTVA(TauxTVA.NORMAL); // suppose 1.2f

        assertEquals(180, menu.getTotalPrice());
    }

    @Test
    void shouldSetAndGetTotalPrice() {
        Menu menu = new Menu();
        menu.setTotalPrice(350);
        assertEquals(350, menu.getPrixHT());
    }
}
