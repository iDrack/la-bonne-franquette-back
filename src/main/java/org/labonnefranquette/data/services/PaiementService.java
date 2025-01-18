package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Paiement;

import java.util.List;

public interface PaiementService {
    List<Paiement> getAllPaiement();

    Paiement getPaiementById(Long id);

    Paiement createPaiement(Long idCommande, PaiementCreateDTO paiementCreateDTO) throws RuntimeException;

    List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException;
}
