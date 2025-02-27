package org.labonnefranquette.data.projection;

import org.labonnefranquette.data.model.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Date;

public interface CommandeListeProjection {
    int getNumero();
    Date getDateSaisie();
    int getNbArticle();
    int getPrixHT();
    StatusCommande getStatus();
    PaiementTypeCommande getPaiementType();
}
