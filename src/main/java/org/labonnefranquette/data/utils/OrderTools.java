package org.labonnefranquette.data.utils;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class OrderTools {

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

    public Boolean checkPrice(@NotNull Order commande) {

        int articlesPrice = 0;
        if (commande.getArticles() != null) {
            articlesPrice = commande.getArticles().stream()
                    .mapToInt(article -> article.getQuantity() * article.getTotalPrice())
                    .sum();
        }

        int menusPrice = 0;
        if (commande.getMenus() != null) {
            menusPrice = commande.getMenus().stream()
                    .mapToInt(menu -> menu.getQuantity() * menu.getTotalPrice())
                    .sum();
        }


        int correctPrice = articlesPrice + menusPrice;

        return correctPrice == commande.getTotalPrice();
    }
    public short setOrderNumber() {
        return incrementeCompteur();
    }

    public String setOrderPaymentType(Collection<Payment> paiementSet) {

        if (paiementSet.isEmpty()) {
            return "AUCUN";
        }
        Set<String> typesPaiement = paiementSet.stream()
                .map(Payment::getType)
                .collect(Collectors.toSet());
        if (typesPaiement.size() == 1) {
            return typesPaiement.iterator().next();
        }
        return "MIXED";
    }
}
