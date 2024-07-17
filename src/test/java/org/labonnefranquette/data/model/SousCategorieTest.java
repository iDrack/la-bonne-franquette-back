package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SousCategorieTest {

    private SousCategorie sousCategorie;

    @BeforeEach
    public void setUp() {
        sousCategorie = new SousCategorie();
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
        Categorie categorie = new Categorie();
        sousCategorie.setCategorie(categorie);
        assertEquals(categorie, sousCategorie.getCategorie());
    }

    @Test
    public void setCategorie_setsNullCategorie() {
        sousCategorie.setCategorie(null);
        assertNull(sousCategorie.getCategorie());
    }
}