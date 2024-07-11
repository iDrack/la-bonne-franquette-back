package org.labonnefranquette.data.utils;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommandeTools {

    private static final Map<PaiementTypeCommande, Integer> compteurNumeroCommande = new EnumMap<>(PaiementTypeCommande.class);
    private static int compteurNumeroCommandePhone = 0;
    private static LocalDateTime dernierUsageCompteur = LocalDateTime.now();

    static {
        for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
            compteurNumeroCommande.put(type, 0);
        }
    }

    private static synchronized void verifierEtReinitialiserSiNecessaire() {
        if (ChronoUnit.HOURS.between(dernierUsageCompteur, LocalDateTime.now()) >= 3) {
            for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
                compteurNumeroCommande.put(type, 0);
            }
        }
    }
    private static synchronized int incrementeCompteur(PaiementTypeCommande type) {
        verifierEtReinitialiserSiNecessaire();
        int currentCount = compteurNumeroCommande.get(type);
        if (currentCount > 100) {
            compteurNumeroCommande.put(type, 0);
        } else {
            compteurNumeroCommande.put(type, currentCount + 1);
        }
        dernierUsageCompteur = LocalDateTime.now();
        return compteurNumeroCommande.get(type);
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

    public String calculNumeroCommande(@NotNull Commande commande) {
        if (commande.getPaiementSet().isEmpty()) {
            return String.valueOf(incrementeCompteur(PaiementTypeCommande.AUCUN));
        }
        Set<PaiementTypeCommande> typesPaiement = commande.getPaiementSet().stream()
                .map(Paiement::getType)
                .collect(Collectors.toSet());
        PaiementTypeCommande typeCommande;
        if (typesPaiement.size() == 1) {
            typeCommande = typesPaiement.iterator().next();
        } else {
            typeCommande = PaiementTypeCommande.MIXED;
        }

        int numeroCommande = incrementeCompteur(typeCommande);
        return typeCommande.name() + numeroCommande;
    }

    public PaiementTypeCommande calculPaiementTypeCommande(String numeroCommande) {
        for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
            if (numeroCommande.startsWith(type.name())) {
                return type;
            }
        }
        return PaiementTypeCommande.AUCUN;
    }
}
