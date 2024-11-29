package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

import java.sql.Date;

@Data
public class PaiementReadDTO  {
    private long id;
    private long commandeId;
    private Date date;
    private PaiementTypeCommande type;
    private Boolean ticketEnvoye;
    private int prixHT;
    private int prixTTC;
}