package org.labonnefranquette.data.projection;

import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Date;

public interface CommandeListeProjection {
    long getId();
    short getNumero();
    Date getDateSaisie();
    Date getDateLivraison();
    int getPrixTTC();
    Boolean getSurPlace();
    StatusCommande getStatus();
    String getPaiementType();
    boolean getPaye();
}
