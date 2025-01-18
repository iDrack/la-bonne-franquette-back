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
    public Commande findCommandeById(long id) {
        return commandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Commande n'existe pas."));
    }

    @Override
    @Transactional
    public Commande createCommande(Commande commande) throws PriceException {
        commande.setDateSaisie(new Date());
        commande.setNbArticle(commande.getArticles().size() + commande.getMenus().size());
        if (!commandeTools.isCorrectPrice(commande)) {
            throw new PriceException("Le prix saisie est incorrect");
        }
        commande.setNumero(commandeTools.calculNumeroCommande());
        commande.setPaiementType(commandeTools.calculPaiementTypeCommande(commande.getPaiementSet()));
        return commandeRepository.save(commande);
    }

    @Override
    public Boolean deleteCommande(Long id) {

        if (commandeRepository.findById(id).isEmpty()) {
            return false;
        }
        commandeRepository.deleteById(id);
        return true;
    }

    @Override
    public Commande ajoutPaiement(Commande commande, Paiement paiement) {
        Commande commandeToUpdate = commandeRepository.findById(commande.getId()).orElseThrow(() -> new RuntimeException("Commande n'existe pas."));
        commandeToUpdate.getPaiementSet().add(paiement);
        commandeToUpdate.setPaiementType(commandeTools.calculPaiementTypeCommande(commandeToUpdate.getPaiementSet()));
        return commandeRepository.save(commandeToUpdate);
    }

    @Override
    public Commande advanceStatusCommande(Long id) throws RuntimeException {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Commande n'existe pas."));

        if (commande.getStatus().equals(StatusCommande.EN_COURS)) {
            commande.setStatus(StatusCommande.TERMINEE);
            return commandeRepository.save(commande);
        }
        return commande;
    }
}
