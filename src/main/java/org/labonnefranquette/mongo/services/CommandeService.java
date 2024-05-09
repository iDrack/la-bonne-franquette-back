package org.labonnefranquette.mongo.services;

import org.labonnefranquette.mongo.model.Commande;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    List<Commande> findAllCommande();

    Optional<Commande> findCommandeById(Long id);

    Commande createCommande(Commande commande);

    Boolean deleteCommande(Long id);

    Commande updateCommande(Commande commande);

    Commande ajoutPaiement(Commande commande, Long paiementId);

    Commande updateProduitsEtExtras(Commande commande, List<List<Long>> produitsEtExtrasIds);

    <T> Commande updateCommandeByField(Commande commande, String fieldName, T value);

    Commande advanceStatusCommande(Commande commande) throws RuntimeException;
}