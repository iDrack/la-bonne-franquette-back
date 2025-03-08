package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.PaiementReadDTO;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaiementReadDTOTest {

    @Test
    void shouldCreatePaiementReadDTOWithValidData() {
        PaiementType type = new PaiementType(1L, "CB", true, new ArrayList<Paiement>());
        PaiementReadDTO dto = new PaiementReadDTO();
        dto.setId(1L);
        dto.setCommandeId(100L);
        dto.setDate(Date.valueOf("2023-01-01"));
        dto.setType(type);
        dto.setPrix(500);
        dto.setArticles(Collections.emptyList());

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getCommandeId());
        assertEquals(Date.valueOf("2023-01-01"), dto.getDate());
        assertEquals(type, dto.getType());
        assertEquals(500, dto.getPrix());
        assertNotNull(dto.getArticles());
    }

    @Test
    void shouldHandleNullArticles() {
        PaiementReadDTO dto = new PaiementReadDTO();
        dto.setArticles(null);

        assertEquals(null, dto.getArticles());
    }

    @Test
    void shouldHandleNegativePrix() {
        PaiementReadDTO dto = new PaiementReadDTO();
        dto.setPrix(-100);

        assertEquals(-100, dto.getPrix());
    }
}