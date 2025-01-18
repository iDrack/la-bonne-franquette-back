package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.repository.PaiementRepository;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id).orElseThrow(() -> new RuntimeException("Le Paiement n'existe pas."));
    }

    @Override
    public Paiement createPaiement(Long idCommande, PaiementCreateDTO paiementCreateDTO) throws RuntimeException {
        Commande commande = commandeService.findCommandeById(idCommande);
        Paiement nouveauPaiement = new Paiement(paiementCreateDTO.getType(), paiementCreateDTO.getTicketEnvoye(), paiementCreateDTO.getPrixTTC(), paiementCreateDTO.getPrixTTC() * commande.getTauxTVA(), commande);
        paiementRepository.save(nouveauPaiement);
        if (commande.getPaiementSet() == null) {
            commande.setPaiementSet(new ArrayList<Paiement>());
        }
        commande.getPaiementSet().add(nouveauPaiement);
        commandeService.ajoutPaiement(commande, nouveauPaiement);
        return nouveauPaiement;
    }

    @Override
    public List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException {
        return paiementRepository.findByCommandeId(commandeId).orElseThrow(() -> new RuntimeException("Aucun paiement n'éxiste pour cette commande."));
    }
}
