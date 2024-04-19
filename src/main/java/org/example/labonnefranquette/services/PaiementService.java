package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.Paiement;

import java.util.List;
import java.util.Optional;

public interface PaiementService {
    public List<Paiement> getAllPaiement();

    public Optional<Paiement> getPaiementById(Long id);

    public Paiement createPaiement(Long idCommande, String type, Boolean ticketEnvoye, int prixPaye) throws RuntimeException;
}
