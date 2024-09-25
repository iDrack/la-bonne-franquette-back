package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        ingredient.setNom("testNom");
        assertEquals("testNom", ingredient.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        ingredient.setNom(null);
        assertNull(ingredient.getNom());
    }

    @Test
    public void setACuire_setsACuireCorrectly() {
        ingredient.setACuire(true);
        assertTrue(ingredient.isACuire());
    }

    @Test
    public void setACuire_setsACuireToFalse() {
        ingredient.setACuire(false);
        assertFalse(ingredient.isACuire());
    }

    @Test
    public void setExtra_setsExtraCorrectly() {
        Extra extra = new Extra();
        ingredient.setExtra(extra);
        assertEquals(extra, ingredient.getExtra());
    }

    @Test
    public void setExtra_setsNullExtra() {
        ingredient.setExtra(null);
        assertNull(ingredient.getExtra());
    }
}
