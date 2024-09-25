package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ProduitTest {

    private Produit produit;

    @BeforeEach
    public void setUp() {
        produit = new Produit();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        produit.setNom("testNom");
        assertEquals("testNom", produit.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        produit.setNom(null);
        assertNull(produit.getNom());
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        produit.setPrixHT(100);
        assertEquals(100, produit.getPrixHT());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        produit.setPrixHT(-100);
        assertEquals(-100, produit.getPrixHT());
    }

    @Test
    public void setCategorieSet_setsCategorieSetCorrectly() {
        Categorie categorie = new Categorie();
        produit.setCategorieSet(Arrays.asList(categorie));
        assertTrue(produit.getCategorieSet().contains(categorie));
    }

    @Test
    public void setCategorieSet_setsEmptyCategorieSet() {
        produit.setCategorieSet(Collections.emptyList());
        assertTrue(produit.getCategorieSet().isEmpty());
    }

    @Test
    public void setIngredientSet_setsIngredientSetCorrectly() {
        Ingredient ingredient = new Ingredient();
        produit.setIngredientSet(Arrays.asList(ingredient));
        assertTrue(produit.getIngredientSet().contains(ingredient));
    }

    @Test
    public void setIngredientSet_setsEmptyIngredientSet() {
        produit.setIngredientSet(Collections.emptyList());
        assertTrue(produit.getIngredientSet().isEmpty());
    }
}