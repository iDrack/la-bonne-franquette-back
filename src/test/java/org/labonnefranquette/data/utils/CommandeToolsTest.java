package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandeToolsTest {

    private CommandeTools commandeTools;

    @BeforeEach
    public void setup() {
        commandeTools = new CommandeTools();
    }

    @Test
    public void isCorrectPrice_returnsTrueWhenPricesMatch() {
        Commande commande = new Commande();

        Article mockArticle = Mockito.mock(Article.class);

        Mockito.when(mockArticle.getQuantite()).thenReturn(1);
        Mockito.when(mockArticle.getPrixHT()).thenReturn(100);

        commande.setArticles(new ArrayList<>(List.of(mockArticle)));

        commande.setPrixHT(100);
        assertTrue(commandeTools.isCorrectPrice(commande));
    }

    @Test
    public void calculPaiementTypeCommande_returnsAucunWhenNoPaiement() {
        assertEquals(PaiementTypeCommande.AUCUN, commandeTools.calculPaiementTypeCommande(Collections.emptyList()));
    }

    @Test
    public void calculPaiementTypeCommande_returnsSingleTypeWhenOnePaiement() {
        Paiement paiement = new Paiement();
        paiement.setType(PaiementTypeCommande.CB);
        assertEquals(PaiementTypeCommande.CB, commandeTools.calculPaiementTypeCommande(Collections.singletonList(paiement)));
    }

    @Test
    public void calculPaiementTypeCommande_returnsMixedWhenMultiplePaiementTypes() {
        Paiement paiement1 = new Paiement();
        paiement1.setType(PaiementTypeCommande.CB);
        Paiement paiement2 = new Paiement();
        paiement2.setType(PaiementTypeCommande.AUTRE);
        assertEquals(PaiementTypeCommande.MIXED, commandeTools.calculPaiementTypeCommande(Arrays.asList(paiement1, paiement2)));
    }
}