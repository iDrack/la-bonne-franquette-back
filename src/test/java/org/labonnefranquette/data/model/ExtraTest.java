package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
public class ExtraTest {

    private Extra extra;

    @BeforeEach
    public void setUp() {
        extra = new Extra();
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        extra.setPrixHT(100);
        assertEquals(100, extra.getPrixHT());
    }

    @Test
    public void setIngredient_setsIngredientCorrectly() {
        Ingredient ingredient = new Ingredient();
        extra.setIngredient(ingredient);
        assertEquals(ingredient, extra.getIngredient());
    }

    @Test
    public void setIngredient_setsNullIngredient() {
        extra.setIngredient(null);
        assertNull(extra.getIngredient());
    }
}
