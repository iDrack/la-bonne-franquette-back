package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.Commande;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    public List<Commande> findAllCommande();

    public Optional<Commande> findCommandeById(Long id);

    public Commande createCommande(int numero,
                                   Boolean surPlace,
                                   Collection<Long> menuSet,
                                   Map<Long, List<Long>> produits);
}
