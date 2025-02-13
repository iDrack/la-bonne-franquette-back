package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class PaiementTest {

    private Paiement paiement;
    private PaiementTypeCommande paiementType;
    @BeforeEach
    public void setUp() {

        paiement = new Paiement();
        paiementType = new PaiementTypeCommande(1L, "CB", true, new ArrayList<>());
    }

    @Test
    public void setPrixHT_setsPrixHTCorrectly() {
        paiement.setPrixHT(100);
        assertEquals(100, paiement.getPrixHT());
    }

    @Test
    public void setPrixTTC_setsPrixTTCCorrectly() {
        paiement.setPrixTTC(110);
        assertEquals(110, paiement.getPrixTTC());
    }

    @Test
    public void setType_setsTypeCorrectly() {
        paiement.setType(paiementType);
        assertEquals(paiementType, paiement.getType());
    }

    @Test
    public void setTicketEnvoye_setsTicketEnvoyeCorrectly() {
        paiement.setTicketEnvoye(true);
        assertEquals(true, paiement.getTicketEnvoye());
    }

    @Test
    public void setDate_setsDateCorrectly() {
        Date date = new Date(System.currentTimeMillis());
        paiement.setDate(date);
        assertEquals(date, paiement.getDate());
    }

    @Test
    public void setCommande_setsCommandeCorrectly() {
        Commande commande = new Commande();
        paiement.setCommande(commande);
        assertEquals(commande, paiement.getCommande());
    }
}
