package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.PaiementType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PaiementService {
    List<Paiement> getAllPaiement();

    Paiement getPaiementById(Long id);

    Paiement createPaiement(Long idCommande, Paiement paiement) throws RuntimeException;

    List<Paiement> getPaiementByCommande(Long commandeId) throws RuntimeException;

    Path generatePDF(Paiement paiement) throws IOException;

    List<PaiementType> getAllPaiementType();
}
