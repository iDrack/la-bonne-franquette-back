package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.entity.Article;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class CommandeToolsTest {

    private OrderTools commandeTools;

    @BeforeEach
    public void setup() {
        commandeTools = new OrderTools();
    }

    @Test
    public void isCorrectPrice_returnsTrueWhenPricesMatch() {
        Order commande = new Order();

        Article mockArticle = Mockito.mock(Article.class);

        Mockito.when(mockArticle.getQuantite()).thenReturn(1);
        Mockito.when(mockArticle.getPrixTTC()).thenReturn(100);

        commande.setArticles(new ArrayList<>(List.of(mockArticle)));

        commande.setTotalPrice(100);
        assertTrue(commandeTools.checkPrice(commande));
    }
    @Test
    void calculNumeroCommande_incrementsCounterCorrectly() {
        short firstNumber = commandeTools.setOrderNumber();
        short secondNumber = commandeTools.setOrderNumber();

        assertEquals(firstNumber + 1, secondNumber);
    }

    @Test
    void calculPaiementTypeCommande_returnsAUCUNWhenNoPayments() {
        Set<Payment> paiements = Collections.emptySet();

        assertEquals("AUCUN", commandeTools.setOrderPaymentType(paiements));
    }

    @Test
    void calculPaiementTypeCommande_returnsSingleTypeWhenAllPaymentsSame() {
        Payment paiement = new Payment();
        paiement.setType("CREDIT_CARD");
        Set<Payment> paiements = new HashSet<>();
        paiements.add(paiement);

        assertEquals("CREDIT_CARD", commandeTools.setOrderPaymentType(paiements));
    }

    @Test
    void calculPaiementTypeCommande_returnsMIXEDWhenPaymentsDifferent() {
        Payment paiement1 = new Payment();
        paiement1.setType("CREDIT_CARD");
        Payment paiement2 = new Payment();
        paiement2.setType("CASH");
        Set<Payment> paiements = new HashSet<>();
        paiements.add(paiement1);
        paiements.add(paiement2);

        assertEquals("MIXED", commandeTools.setOrderPaymentType(paiements));
    }
}