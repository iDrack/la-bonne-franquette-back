package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class PaiementTest {

    private Paiement paiement;
    private PaiementType paiementType;
    private Commande commande;
    private Collection<Article> articles;

    @BeforeEach
    public void setUp() {
        paiementType = new PaiementType(1L, "CB", true, new ArrayList<>());
        commande = new Commande();
        articles = new ArrayList<>();
        paiement = new Paiement(paiementType, 100, commande, articles);
    }

    @Test
    public void testSetPrix() {
        paiement.setPrix(200);
        assertEquals(200, paiement.getPrix());
    }

    @Test
    public void testSetType() {
        PaiementType newType = new PaiementType(2L, "Cash", true, new ArrayList<>());
        paiement.setType(newType);
        assertEquals(newType, paiement.getType());
    }

    @Test
    public void testSetDate() {
        Date date = new Date(System.currentTimeMillis());
        paiement.setDate(date);
        assertEquals(date, paiement.getDate());
    }

    @Test
    public void testSetCommande() {
        Commande newCommande = new Commande();
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