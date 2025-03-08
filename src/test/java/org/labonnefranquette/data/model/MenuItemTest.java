package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class MenuItemTest {

    private MenuItem menuItem;
    private Produit produit1;
    private Produit produit2;

    @BeforeEach
    public void setUp() {
        menuItem = new MenuItem();
        produit1 = new Produit();
        produit1.setId(1L);
        produit1.setNom("Produit 1");
        produit1.setPrixHT(100);

        produit2 = new Produit();
        produit2.setId(2L);
        produit2.setNom("Produit 2");
        produit2.setPrixHT(200);
    }

    @Test
    public void setProduits_setsProduitsCorrectly() {
        menuItem.setProduitSet(Arrays.asList(produit1, produit2));
        assertEquals(2, menuItem.getProduitSet().size());
        assertTrue(menuItem.getProduitSet().contains(produit1));
        assertTrue(menuItem.getProduitSet().contains(produit2));
    }

    @Test
    public void setProduits_setsEmptyProduits() {
        menuItem.setProduitSet(Collections.emptyList());
        assertTrue(menuItem.getProduitSet().isEmpty());
    }


    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        menuItem.setPrixHT(100);
        assertEquals(100, menuItem.getPrixHT());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        menuItem.setPrixHT(-100);
        assertEquals(-100, menuItem.getPrixHT());
    }

    @Test
    public void setOptional_setsOptionalCorrectly() {
        menuItem.setOptional(true);
        assertTrue(menuItem.isOptional());
    }
}