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

    @BeforeEach
    public void setUp() {
        menu = new Menu();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        menu.setNom("testNom");
        assertEquals("testNom", menu.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        menu.setNom(null);
        assertNull(menu.getNom());
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
    public void setProduitSet_setsProduitSetCorrectly() {
        Produit produit = new Produit();
        menu.setProduitSet(Arrays.asList(produit));
        assertTrue(menu.getProduitSet().contains(produit));
    }

    @Test
    public void setProduitSet_setsEmptyProduitSet() {
        menu.setProduitSet(Collections.emptyList());
        assertTrue(menu.getProduitSet().isEmpty());
    }
}
