package org.labonnefranquette.data.utils;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommandeTools {

    private static short compteurNumeroCommande = 0;
    private static LocalDateTime dernierUsageCompteur = LocalDateTime.now();


    private static synchronized void verifierEtReinitialiserSiNecessaire() {
        if (ChronoUnit.HOURS.between(dernierUsageCompteur, LocalDateTime.now()) >= 3) {
            compteurNumeroCommande = 0;
        }
    }
    private static synchronized short incrementeCompteur() {
        verifierEtReinitialiserSiNecessaire();
        if (compteurNumeroCommande > 400) {
            compteurNumeroCommande = 0;
        }
        compteurNumeroCommande++;
        dernierUsageCompteur = LocalDateTime.now();
        return compteurNumeroCommande;
    }

    public Boolean isCorrectPrice(@NotNull  Commande commande) {

        int articlesPrice = commande.getArticles().stream()
            .mapToInt(article -> article.getQuantite() * article.getPrixHT())
            .sum();

        int menusPrice = commande.getMenus().stream()
            .mapToInt(menu -> menu.getQuantite() * menu.getPrixHT())
            .sum();

        int correctPrice = articlesPrice + menusPrice;

        return correctPrice == commande.getPrixHT();
    }
    public short calculNumeroCommande() {
        return incrementeCompteur();
    }
    public PaiementTypeCommande calculPaiementTypeCommande(Collection<Paiement> paiementSet) {
        if (paiementSet.isEmpty()) {
            return PaiementTypeCommande.AUCUN;
        }
        Set<PaiementTypeCommande> typesPaiement = paiementSet.stream()
                .map(Paiement::getType)
                .collect(Collectors.toSet());
        if (typesPaiement.size() == 1) {
            return typesPaiement.iterator().next();
        }
        return PaiementTypeCommande.MIXED;
    }
}
