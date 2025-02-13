package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementTypeCommande;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PaiementService {
    List<Paiement> getAllPaiement();

    Paiement getPaiementById(Long id);

    Paiement createPaiement(Long idCommande, PaiementCreateDTO paiementCreateDTO) throws RuntimeException;

    List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException;

    Path generatePDF(Paiement paiement) throws IOException;

    List<PaiementTypeCommande> getAllPaiementType();
}
