package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
public class PaiementTypeTest {

    private PaymentType paiementType;

    @BeforeEach
    public void setUp() {
        paiementType = new PaymentType(1L, "CB", true, new ArrayList<>());
    }

    @Test
    public void testSetName() {
        paiementType.setName("Cash");
        assertEquals("Cash", paiementType.getName());
    }

    @Test
    public void testSetIsEnable() {
        paiementType.setIsEnable(false);
        assertFalse(paiementType.getIsEnable());
    }

    @Test
    public void testSetPaiements() {
        ArrayList<Payment> paiements = new ArrayList<>();
        paiementType.setPaiements(paiements);
        assertEquals(paiements, paiementType.getPaiements());
    }
}
