package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.List;
import java.util.Map;

public interface CommandeService {

    List<Commande> findAllCommandeWithStatut(StatusCommande status, String token);

    Commande findCommandeById(long id) throws NullPointerException;
    Commande createCommande(Commande commande, String token);
    Boolean deleteCommande(Long id);
    Commande ajoutPaiement(Commande commande, Paiement paiement);
    Commande advanceStatusCommande(Long id) throws RuntimeException;

    Commande patchCommande(Long id, Map<String, Object> updates);
}