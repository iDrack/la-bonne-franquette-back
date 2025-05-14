package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class PaiementTest {

    private Payment paiement;
    private PaymentType paiementType;
    private Order commande;
    private Collection<Article> articles;
    private Collection<Selection> selections;

    @BeforeEach
    public void setUp() {
        commande = new Order();
        articles = new ArrayList<>();
        selections = new ArrayList<>();
        paiement = new Payment("CB", 100, commande, articles, selections);
    }

    @Test
    public void testSetPrix() {
        paiement.setPrix(200);
        assertEquals(200, paiement.getPrix());
    }

    @Test
    public void testSetType() {
        paiement.setType("Cash");
        assertEquals("Cash", paiement.getType());
    }

    @Test
    public void testSetDate() {
        Date date = new Date(System.currentTimeMillis());
        paiement.setDate(date);
        assertEquals(date, paiement.getDate());
    }

    @Test
    public void testSetCommande() {
        Order newCommande = new Order();
        paiement.setCommande(newCommande);
        assertEquals(newCommande, paiement.getCommande());
    }

    @Test
    public void testSetArticles() {
        Collection<Article> newArticles = new ArrayList<>();
        paiement.setArticles(newArticles);
        assertEquals(newArticles, paiement.getArticles());
    }
}