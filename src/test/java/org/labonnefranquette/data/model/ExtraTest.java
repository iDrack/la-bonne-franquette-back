package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class ExtraTest {

    private Extra extra;

    @BeforeEach
    public void setUp() {
        Set<Produit> produitSet = new HashSet<>();
        extra = new Extra(1L, "Extra Cheese", produitSet);
    }

    @Test
    public void testSetNom() {
        extra.setNom("Extra Bacon");
        assertEquals("Extra Bacon", extra.getNom());
    }

    @Test
    public void testSetProduitSet() {
        Set<Produit> newProduitSet = new HashSet<>();
        extra.setProduitSet(newProduitSet);
        assertEquals(newProduitSet, extra.getProduitSet());
    }
}