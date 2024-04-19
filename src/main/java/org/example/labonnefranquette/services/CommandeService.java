package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.Commande;
import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.model.Menu;
import org.example.labonnefranquette.model.Produit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandeService {

    public List<Commande> findAllCommande();

    public Optional<Commande> findCommandeById(Long id);

    public Commande createCommande(int numero,
                                   Boolean surPlace,
                                   Collection<Menu> menuSet,
                                   Map<Produit, List<Extra>> produits);
}
