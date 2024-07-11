package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.utils.CommandeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeTools commandeTools;

    @Override
    public List<Commande> findAllCommande() {
        return commandeRepository.findAll();
    }
    @Override
    public List<Commande> findAllCommandeWithStatut(StatusCommande status) {
        return commandeRepository.findAllCommandeWithStatut(status);
    }
    @Override
    public List<CommandeListeProjection> findAllCommandeListe()  {
        return commandeRepository.findAllCommandeListe();
    }
    @Override
    public Optional<Commande> findCommandeById(long id) {
        return commandeRepository.findById(id);
    }

    @Override
    @Transactional
    public Commande createCommande(Commande commande) throws PriceException {
        commande.setDateSaisie(new Date());
        commande.setNbArticle(commande.getArticles().size() + commande.getMenus().size());
        if (!commandeTools.isCorrectPrice(commande)) {
            throw new PriceException("Le prix saisie est incorrect");
        }
        commande.setNumero(commandeTools.calculNumeroCommande(commande));
        commande.setPaiementType(commandeTools.calculPaiementTypeCommande(commande.getNumero()));
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
            commandeFound.get().setStatus(StatusCommande.TERMINEE);
            return commandeRepository.save(commandeFound.get());
        }
        return commandeFound.get();
    }
}
