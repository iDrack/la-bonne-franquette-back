package org.labonnefranquette.data.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

import static org.junit.jupiter.api.Assertions.*;

public class PaiementTypeCommandeEntityTest {

    private PaiementTypeCommandeEntity entity;

    @BeforeEach
    public void setUp() {
        entity = new PaiementTypeCommandeEntity();
    }

    @Test
    public void setType_setsTypeCorrectly() {
        entity.setType(PaiementTypeCommande.CB);
        assertEquals(PaiementTypeCommande.CB, entity.getType());
    }

    @Test
    public void setType_setsNullType() {
        entity.setType(null);
        assertNull(entity.getType());
    }

    @Test
    public void setEnable_setsEnableTrue() {
        entity.setEnable(true);
        assertTrue(entity.isEnable());
    }

    @Test
    public void setEnable_setsEnableFalse() {
        entity.setEnable(false);
        assertFalse(entity.isEnable());
    }
}