package org.labonnefranquette.data.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class SelectionTest {

    private Selection selection;

    @BeforeEach
    public void setUp() {
        selection = new Selection();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        selection.setNom("testNom");
        assertEquals("testNom", selection.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        selection.setNom(null);
        assertNull(selection.getNom());
    }

    @Test
    public void setQuantite_setsQuantiteCorrectly() {
        selection.setQuantite(10);
        assertEquals(10, selection.getQuantite());
    }

    @Test
    public void setQuantite_setsNegativeQuantite() {
        selection.setQuantite(-10);
        assertEquals(-10, selection.getQuantite());
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        selection.setPrixHT(100);
        assertEquals(100, selection.getPrixHT());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        selection.setPrixHT(-100);
        assertEquals(-100, selection.getPrixHT());
    }

    @Test
    public void setArticles_setsArticlesCorrectly() {
        Article article = new Article();
        selection.setArticles(Arrays.asList(article));
        assertTrue(selection.getArticles().contains(article));
    }

    @Test
    public void setArticles_setsEmptyArticles() {
        selection.setArticles(Collections.emptyList());
        assertTrue(selection.getArticles().isEmpty());
    }
}