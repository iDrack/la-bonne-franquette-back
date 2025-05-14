package org.labonnefranquette.data.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Ingredient;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class ArticleTest {

    private Article article;

    @BeforeEach
    public void setUp() {
        article = new Article();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        article.setNom("testNom");
        assertEquals("testNom", article.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        article.setNom(null);
        assertNull(article.getNom());
    }

    @Test
    public void setQuantite_setsQuantiteCorrectly() {
        article.setQuantite(10);
        assertEquals(10, article.getQuantite());
    }

    @Test
    public void setQuantite_setsNegativeQuantite() {
        article.setQuantite(-10);
        assertEquals(-10, article.getQuantite());
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        article.setPrixTTC(100);
        assertEquals(100, article.getPrixTTC());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        article.setPrixTTC(-100);
        assertEquals(-100, article.getPrixTTC());
    }

    @Test
    public void setIngredients_setsIngredientsCorrectly() {
        Ingredient ingredient = new Ingredient();
        article.setIngredients(Arrays.asList(ingredient));
        assertTrue(article.getIngredients().contains(ingredient));
    }

    @Test
    public void setIngredients_setsEmptyIngredients() {
        article.setIngredients(Collections.emptyList());
        assertTrue(article.getIngredients().isEmpty());
    }

    @Test
    public void setExtraSet_setsExtraSetCorrectly() {
        Addon extra = new Addon();
        article.setExtraSet(Arrays.asList(extra));
        assertTrue(article.getExtraSet().contains(extra));
    }

    @Test
    public void setExtraSet_setsEmptyExtraSet() {
        article.setExtraSet(Collections.emptyList());
        assertTrue(article.getExtraSet().isEmpty());
    }
}
