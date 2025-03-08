package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.model.entity.Article;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
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
        Mockito.when(mockArticle.getPrixTTC()).thenReturn(100);

        commande.setArticles(new ArrayList<>(List.of(mockArticle)));

        commande.setPrixTTC(100);
        assertTrue(commandeTools.isCorrectPrice(commande));
    }
    @Test
    void calculNumeroCommande_incrementsCounterCorrectly() {
        short firstNumber = commandeTools.calculNumeroCommande();
        short secondNumber = commandeTools.calculNumeroCommande();

        assertEquals(firstNumber + 1, secondNumber);
    }

    @Test
    void calculPaiementTypeCommande_returnsAUCUNWhenNoPayments() {
        Set<Paiement> paiements = Collections.emptySet();

        assertEquals("AUCUN", commandeTools.calculPaiementTypeCommande(paiements));
    }

    @Test
    void calculPaiementTypeCommande_returnsSingleTypeWhenAllPaymentsSame() {
        PaiementType type = new PaiementType();
        type.setName("CREDIT_CARD");
        Paiement paiement = new Paiement();
        paiement.setType(type);
        Set<Paiement> paiements = new HashSet<>();
        paiements.add(paiement);

        assertEquals("CREDIT_CARD", commandeTools.calculPaiementTypeCommande(paiements));
    }

    @Test
    void calculPaiementTypeCommande_returnsMIXEDWhenPaymentsDifferent() {
        PaiementType type1 = new PaiementType();
        type1.setName("CREDIT_CARD");
        PaiementType type2 = new PaiementType();
        type2.setName("CASH");
        Paiement paiement1 = new Paiement();
        paiement1.setType(type1);
        Paiement paiement2 = new Paiement();
        paiement2.setType(type2);
        Set<Paiement> paiements = new HashSet<>();
        paiements.add(paiement1);
        paiements.add(paiement2);

        assertEquals("MIXED", commandeTools.calculPaiementTypeCommande(paiements));
    }
}