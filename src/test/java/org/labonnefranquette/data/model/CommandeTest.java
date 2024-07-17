package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandeTest {

    private Commande commande;

    @BeforeEach
    public void setUp() {
        commande = new Commande();
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
    public void setPrixHT_setsPrixHTCorrectly() {
        commande.setPrixHT(100);
        assertEquals(100, commande.getPrixHT());
    }

    @Test
    public void setPaiementType_setsPaiementTypeCorrectly() {
        commande.setPaiementType(PaiementTypeCommande.CB);
        assertEquals(PaiementTypeCommande.CB, commande.getPaiementType());
    }
}