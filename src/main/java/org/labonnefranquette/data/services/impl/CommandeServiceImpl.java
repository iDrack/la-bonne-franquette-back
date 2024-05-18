package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.StatusCommande;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public List<Commande> findAllCommande() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findCommandeById(long id) {
        return commandeRepository.findById(id);
    }

    @Override
    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public Boolean deleteCommande(long id) {

        if (commandeRepository.findById(id).isEmpty()) {
            return false;
        }
        commandeRepository.deleteById(id);
        return true;
    }

    @Override
    public Commande ajoutPaiement(Commande commande, Paiement paiement) {
        Optional<Commande> commandeFound = commandeRepository.findById(commande.getId());
        if (commandeFound.isEmpty()) {
            return null;
        }
        Commande commandeToUpdate = commandeFound.get();
        commandeToUpdate.getPaiementSet().add(paiement);
        return commandeRepository.save(commandeToUpdate);
    }

    @Override
    public Commande advanceStatusCommande(Long id) throws RuntimeException {
        Optional<Commande> commandeFound = commandeRepository.findById(id);
        if (commandeFound.isEmpty()) {
            throw new RuntimeException("Commande not found");
        }
        if (commandeFound.get().getStatus().equals(StatusCommande.EN_COURS)) {
            commandeFound.get().setStatus(StatusCommande.TERMINE);
            return commandeRepository.save(commandeFound.get());
        }
        return commandeFound.get();
    }
}
