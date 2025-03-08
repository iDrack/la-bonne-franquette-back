package org.labonnefranquette.data.projection;

import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Date;

public interface CommandeListeProjection {
    int getNumero();
    Date getDateSaisie();
    int getNbArticle();
    int getPrixHT();
    StatusCommande getStatus();

    PaiementType getPaiementType();
}
