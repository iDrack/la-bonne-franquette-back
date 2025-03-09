package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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

}
