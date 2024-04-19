package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Commande;
import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.model.Menu;
import org.example.labonnefranquette.model.Produit;
import org.example.labonnefranquette.repository.CommandeRepository;
import org.example.labonnefranquette.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    CommandeRepository commandeRepository;

    @Override
    public List<Commande> findAllCommande() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    /**
     * Permet de de créer une commande à partir des éléments passés en paramètre.
     *
     * @param numero,        (int) Numéro de commande
     * @param surPlace,      (Boolean) Permettant de savoir si la commande est sur place
     * @param prixHT,        (int) Prix hors taxe de la commande (à diviser par 100 pour obtenir son prix)
     * @param menuSet,       (Collection<Menu>) Liste de menu contenu dans la commande
     * @param produitsExtra, (Map<Produit, List<Extra>>) Map de produits et leurs extras
     * @return Commande, La commande créer grâce aux infos passés en paramètre
     */
    @Override
    public Commande createCommande(int numero,
                                   Boolean surPlace,
                                   Collection<Menu> menuSet,
                                   Map<Produit, List<Extra>> produitsExtra) {


        int prixHTTotal = 0;
        //Ajout au total final le prixHT de tous les produits et de leurs extras
        //TODO: Gérer les prixHT des menus
        for (Produit produit : produitsExtra.keySet()) {
            List<Extra> extras = produitsExtra.get(produit);
            for (Extra extra : extras) {
                prixHTTotal += extra.getPrixHT();
            }
            prixHTTotal += produit.getPrixHT();
        }

        //Envoyer en cuisine la commande (avec les extras des produits)
        //TODO: A faire

        //Enregistrement de la commande dans la bdd
        Commande retCommande = new Commande(numero, surPlace, prixHTTotal, produitsExtra.keySet(), menuSet);
        commandeRepository.save(retCommande);
        return retCommande;
    }
}
