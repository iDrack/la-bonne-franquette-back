package org.labonnefranquette.mongo.services.impl;

import org.labonnefranquette.mongo.model.Commande;
import org.labonnefranquette.mongo.repository.CommandeRepository;
import org.labonnefranquette.mongo.services.CommandeService;
import org.labonnefranquette.sql.repository.ExtraRepository;
import org.labonnefranquette.sql.repository.MenuRepository;
import org.labonnefranquette.sql.repository.ProduitRepository;
import org.labonnefranquette.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    ExtraRepository extraRepository;

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private DtoTools dtoTools;

    @Override
    public List<Commande> findAllCommande() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    /**
     * Permet de créer une commande à partir des éléments passés en paramètre.
     *
     * @param commande, Commande à enregistrer,
     * @return Commande, La commande créée grâce aux infos passées en paramètre
     */
    @Override
    public Commande createCommande(Commande commande) {
        commandeRepository.save(commande);
        return commande;
    }

    @Override
    public Boolean deleteCommande(Long id) {
        Optional<Commande> commandeFound = findCommandeById(id);
        if (commandeFound.isEmpty()) {
            return false;
        }
        commandeRepository.delete(commandeFound.get());
        return true;
    }


}
