package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class ExtraTest {

    private Addon extra;

    @BeforeEach
    public void setUp() {
        Set<Product> produitSet = new HashSet<>();
        extra = new Addon(1L, "Extra Cheese", produitSet);
    }

    @Test
    public void testSetNom() {
        extra.setName("Extra Bacon");
        assertEquals("Extra Bacon", extra.getName());
    }

    @Test
    public void testSetProduitSet() {
        Set<Product> newProduitSet = new HashSet<>();
        extra.setProducts(newProduitSet);
        assertEquals(newProduitSet, extra.getProducts());
    }
}