package org.labonnefranquette.data.model.enums;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class StatusCommandeTest {

    @Test
    public void enumValues_areCorrect() {
        assertEquals(4, StatusCommande.values().length);
        assertEquals(StatusCommande.EN_COURS, StatusCommande.values()[0]);
        assertEquals(StatusCommande.TERMINEE, StatusCommande.values()[1]);
        assertEquals(StatusCommande.LIVREE, StatusCommande.values()[2]);
        assertEquals(StatusCommande.ANNULEE, StatusCommande.values()[3]);
    }
}
