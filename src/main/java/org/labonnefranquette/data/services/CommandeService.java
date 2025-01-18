package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;

import java.util.List;

public interface CommandeService {

    List<Commande> findAllCommande();
    List<Commande> findAllCommandeWithStatut(StatusCommande status);
    List<CommandeListeProjection> findAllCommandeListe();

    Commande findCommandeById(long id);
    Commande createCommande(Commande commande);

    Boolean deleteCommande(Long id);
    Commande ajoutPaiement(Commande commande, Paiement paiement);
    Commande advanceStatusCommande(Long id) throws RuntimeException;
}