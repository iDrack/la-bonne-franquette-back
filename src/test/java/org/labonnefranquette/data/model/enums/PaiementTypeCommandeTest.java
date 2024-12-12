package org.labonnefranquette.data.model.enums;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class PaiementTypeCommandeTest {

    @Test
    public void enumValues_areCorrect() {
        assertEquals(5, PaiementTypeCommande.values().length);
        assertEquals(PaiementTypeCommande.CB, PaiementTypeCommande.values()[0]);
        assertEquals(PaiementTypeCommande.ESP, PaiementTypeCommande.values()[1]);
        assertEquals(PaiementTypeCommande.AUTRE, PaiementTypeCommande.values()[2]);
        assertEquals(PaiementTypeCommande.MIXED, PaiementTypeCommande.values()[3]);
        assertEquals(PaiementTypeCommande.AUCUN, PaiementTypeCommande.values()[4]);
    }
}
