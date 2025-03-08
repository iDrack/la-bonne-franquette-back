package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class CommandeTest {

    private Commande commande;
    private String paiementType;


    @BeforeEach
    public void setUp() {
        commande = new Commande();
        paiementType = "CB";
    }

    @Test
    public void setNumero_setsNumeroCorrectly() {
        commande.setNumero((short) 123);
        assertEquals(123, commande.getNumero());
    }

    @Test
    public void setDateSaisie_setsDateSaisieCorrectly() {
        Date date = new Date();
        commande.setDateSaisie(date);
        assertEquals(date, commande.getDateSaisie());
    }

    @Test
    public void setDateLivraison_setsDateLivraisonCorrectly() {
        Date date = new Date();
        commande.setDateLivraison(date);
        assertEquals(date, commande.getDateLivraison());
    }

    @Test
    public void setStatus_setsStatusCorrectly() {
        commande.setStatus(StatusCommande.EN_COURS);
        assertEquals(StatusCommande.EN_COURS, commande.getStatus());
    }

    @Test
    public void setSurPlace_setsSurPlaceCorrectly() {
        commande.setSurPlace(true);
        assertEquals(true, commande.getSurPlace());
    }

    @Test
    public void setNbArticle_setsNbArticleCorrectly() {
        commande.setNbArticle(5);
        assertEquals(5, commande.getNbArticle());
    }

    @Test
    public void setPrixTTC_setsPrixTTCCorrectly() {
        commande.setPrixTTC(100);
        assertEquals(100, commande.getPrixTTC());
    }

    @Test
    public void setPaiementType_setsPaiementTypeCorrectly() {
        commande.setPaiementType(paiementType);
        assertEquals(paiementType, commande.getPaiementType());
    }
}