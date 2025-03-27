package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.repository.PaiementRepository;
import org.labonnefranquette.data.repository.PaiementTypeRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.CommandeService;
import org.labonnefranquette.data.services.PaiementService;
import org.labonnefranquette.data.utils.PDFTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
public class PaiementServiceImpl implements PaiementService {

    @Autowired
    PaiementRepository paiementRepository;

    @Autowired
    CommandeService commandeService;
    @Autowired
    RestaurantServiceImpl restaurantService;
    @Autowired
    private PaiementTypeRepository paiementTypeRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public List<Paiement> getAllPaiement() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id).orElseThrow(() -> new RuntimeException("Le Paiement n'existe pas."));
    }

    @Override
    public Paiement createPaiement(Long idCommande, Paiement paiement) throws RuntimeException {
        Commande commande = commandeService.findCommandeById(idCommande);
        paiement.setCommande(commande);
        paiement.setRestaurant(commande.getRestaurant());
        paiementRepository.save(paiement);
        int prixPaye = 0;
        String type = commande.getPaiementType();

        for (Paiement currentPaiement : commande.getPaiementSet()) {
            if (!Objects.equals(type, "AUCUN") && !Objects.equals(currentPaiement.getType(), type)) {
                commande.setPaiementType("MIX");
            } else  {
                commande.setPaiementType(currentPaiement.getType());
            }
            prixPaye += currentPaiement.getPrix();
        }
        if (commande.getPrixTTC() == prixPaye) {
            commande.setStatus(StatusCommande.TERMINEE);
            commande.setPaye(true);
        }
        System.out.println(commande);
        commandeRepository.save(commande);
        return paiement;
    }

    @Override
    public List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException {
        return paiementRepository.findByCommandeId(commandeId).orElseThrow(() -> new RuntimeException("Aucun paiement n'éxiste pour cette commande."));
    }

    @Override
    public List<PaiementType> getAllPaiementType() {
        return paiementTypeRepository.findAll();
    }


    @Override
    public Path generatePDF(Paiement paiement) throws IOException {
        PDFTools pdfTools = PDFTools.getInstance();
        return pdfTools.toPDF(paiement, "tmp.pdf", true);
    }


}
