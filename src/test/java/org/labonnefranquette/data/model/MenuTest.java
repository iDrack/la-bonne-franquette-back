package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class MenuTest {

    private Menu menu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    public void setUp() {

        menu = new Menu();
        menuItem1 = new MenuItem();
        menuItem1.setId(1L);
        menuItem1.setOptional(true);
        menuItem1.setPrixHT(50);

        menuItem2 = new MenuItem();
        menuItem2.setId(2L);
        menuItem2.setOptional(false);
        menuItem2.setPrixHT(100);
    }

    @Test
    public void setNom_setsNomCorrectly() {
        menu.setName("testNom");
        assertEquals("testNom", menu.getName());
    }

    @Test
    public void setNom_setsNullNom() {
        menu.setName(null);
        assertNull(menu.getName());
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        menu.setPrixHT(100);
        assertEquals(100, menu.getPrixHT());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        menu.setPrixHT(-100);
        assertEquals(-100, menu.getPrixHT());
    }

    @Test
    public void setMenuItemSet_setsMenuItemsCorrectly() {
        menu.setMenuItemSet(Arrays.asList(menuItem1, menuItem2));
        assertEquals(2, menu.getMenuItemSet().size());
        assertTrue(menu.getMenuItemSet().contains(menuItem1));
        assertTrue(menu.getMenuItemSet().contains(menuItem2));
    }

    @Test
    public void setMenuItemSet_setsEmptyMenuItems() {
        menu.setMenuItemSet(Collections.emptyList());
        assertTrue(menu.getMenuItemSet().isEmpty());
    }
}
