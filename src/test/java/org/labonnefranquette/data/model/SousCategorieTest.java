package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
public class SousCategorieTest {

    private SubCategory sousCategorie;

    @BeforeEach
    public void setUp() {
        sousCategorie = new SubCategory();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        sousCategorie.setNom("testNom");
        assertEquals("testNom", sousCategorie.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        sousCategorie.setNom(null);
        assertNull(sousCategorie.getNom());
    }

    @Test
    public void setCategorie_setsCategorieCorrectly() {
        Category categorie = new Category();
        sousCategorie.setCategorie(categorie);
        assertEquals(categorie, sousCategorie.getCategorie());
    }

    @Test
    public void setCategorie_setsNullCategorie() {
        sousCategorie.setCategorie(null);
        assertNull(sousCategorie.getCategorie());
    }
}