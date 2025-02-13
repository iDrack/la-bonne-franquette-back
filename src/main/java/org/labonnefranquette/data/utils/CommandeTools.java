package org.labonnefranquette.data.utils;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementTypeCommande;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class CommandeTools {

    private static short compteurNumeroCommande = 0;
    private static AtomicLong dernierUsageCompteur = new AtomicLong(System.currentTimeMillis());


    private static synchronized void verifierEtReinitialiserSiNecessaire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - dernierUsageCompteur.get() >= 3 * 60 * 60 * 1000) { // 3 heures
            compteurNumeroCommande = 0;
        }
    }
    private static synchronized short incrementeCompteur() {
        verifierEtReinitialiserSiNecessaire();
        if (compteurNumeroCommande > 400) {
            compteurNumeroCommande = 0;
        }
        compteurNumeroCommande++;
        dernierUsageCompteur.set(System.currentTimeMillis());
        return compteurNumeroCommande;
    }

    public Boolean isCorrectPrice(@NotNull  Commande commande) {

        int articlesPrice = 0;
        if (commande.getArticles() != null) {
            articlesPrice = commande.getArticles().stream()
                    .mapToInt(article -> article.getQuantite() * article.getPrixHT())
                    .sum();
        }

        int menusPrice = 0;
        if (commande.getMenus() != null) {
            menusPrice = commande.getMenus().stream()
                    .mapToInt(menu -> menu.getQuantite() * menu.getPrixHT())
                    .sum();
        }


        int correctPrice = articlesPrice + menusPrice;

        return correctPrice == commande.getPrixHT();
    }
    public short calculNumeroCommande() {
        return incrementeCompteur();
    }

    public String calculPaiementTypeCommande(Collection<Paiement> paiementSet) {

        if (paiementSet.isEmpty()) {
            return "AUCUN";
        }
        Set<PaiementTypeCommande> typesPaiement = paiementSet.stream()
                .map(Paiement::getType)
                .collect(Collectors.toSet());
        if (typesPaiement.size() == 1) {
            return typesPaiement.iterator().next().getName();
        }
        return "MIXED";
    }
}
