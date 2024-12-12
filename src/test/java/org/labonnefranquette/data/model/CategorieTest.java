package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@Tag("unit")
public class CategorieTest {

    private Categorie categorie;

    @BeforeEach
    public void setUp() {
        categorie = new Categorie();
    }

    @Test
    public void setNom_setsNomCorrectly() {
        categorie.setNom("testNom");
        assertEquals("testNom", categorie.getNom());
    }

    @Test
    public void setNom_setsNullNom() {
        categorie.setNom(null);
        assertNull(categorie.getNom());
    }

    @Test
    public void setCategorieType_setsCategorieTypeCorrectly() {
        categorie.setCategorieType("testType");
        assertEquals("testType", categorie.getCategorieType());
    }

    @Test
    public void setCategorieType_setsNullCategorieType() {
        categorie.setCategorieType(null);
        assertNull(categorie.getCategorieType());
    }
}