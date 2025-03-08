package org.labonnefranquette.data.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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
        selection.setPrixTTC(100);
        assertEquals(100, selection.getPrixTTC());
    }

    @Test
    public void setPrixHT_setsNegativePrixHT() {
        selection.setPrixTTC(-100);
        assertEquals(-100, selection.getPrixTTC());
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