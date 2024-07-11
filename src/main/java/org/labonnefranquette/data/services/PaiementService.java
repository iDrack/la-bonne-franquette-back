package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Paiement;

import java.util.List;
import java.util.Optional;

public interface PaiementService {
    List<Paiement> getAllPaiement();

    Optional<Paiement> getPaiementById(Long id);

    Paiement createPaiement(Long idCommande, PaiementCreateDTO paiementCreateDTO) throws RuntimeException;

    List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException;
}
