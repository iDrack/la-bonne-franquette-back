package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.repository.PaiementRepository;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {

    @Autowired
    PaiementRepository paiementRepository;

    @Autowired
    CommandeService commandeService;

    @Override
    public List<Paiement> getAllPaiement() {
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public Paiement createPaiement(Long idCommande, String type, Boolean ticketEnvoye, int prixPaye) throws RuntimeException {
        Commande commande;
        Optional<Commande> commandeFound = commandeService.findCommandeById(idCommande);
        if (commandeFound.isPresent()) {
            commande = commandeFound.get();
        } else {
            throw new RuntimeException("Commande n'existe pas.");
        }
        Paiement nouveauPaiement = new Paiement(type, ticketEnvoye, prixPaye, prixPaye * commande.getTauxTVA(), commande);
        paiementRepository.save(nouveauPaiement);
        commande.getPaiementSet().add(nouveauPaiement);
        commandeService.ajoutPaiement(commande, nouveauPaiement);
        return nouveauPaiement;
    }

    @Override
    public List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException {
        Optional<List<Paiement>> paiementList = paiementRepository.findByCommandeId(commandeId);
        if (paiementList.isPresent()) {
            return paiementList.get();
        }
        throw new RuntimeException("Aucun paiement n'éxiste pour cette commande.");
    }
}
