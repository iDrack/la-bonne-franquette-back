package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.List;

public interface CommandeService {

    List<Commande> findAllCommandeWithStatut(StatusCommande status, String token);

    Commande findCommandeById(long id);

    Commande createCommande(Commande commande, String token);

    Boolean deleteCommande(Long id);
    Commande ajoutPaiement(Commande commande, Paiement paiement);
    Commande advanceStatusCommande(Long id) throws RuntimeException;
}