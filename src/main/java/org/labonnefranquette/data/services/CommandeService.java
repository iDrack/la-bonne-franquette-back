package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.projection.CommandeListeProjection;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    List<Commande> findAllCommande();
    List<CommandeListeProjection> findAllCommandeListe();
    Optional<Commande> findCommandeById(long id);
    Commande createCommande(Commande commande);
    Boolean deleteCommande(long id);
    Commande ajoutPaiement(Commande commande, Paiement paiement);
    Commande advanceStatusCommande(Long id) throws RuntimeException;
}