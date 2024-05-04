package org.labonnefranquette.mongo.services;

import org.labonnefranquette.mongo.model.Commande;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    public List<Commande> findAllCommande();

    public Optional<Commande> findCommandeById(Long id);

    public Commande createCommande(Commande commande);

    public Boolean deleteCommande(Long id);
}
