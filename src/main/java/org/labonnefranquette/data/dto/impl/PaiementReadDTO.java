package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.dto.PaiementDTO;

import java.sql.Date;

@Data
public class PaiementReadDTO implements PaiementDTO {
    private long id;
    private long commandeId;
    private Date date;
    private String type;
    private Boolean ticketEnvoye;
    private int prixHT;
    private int prixTTC;
}