package org.labonnefranquette.sql.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.mongo.model.Commande;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO implements PaiementDTO {
    private Commande commande;
    private String type;
    private Boolean ticketEnvoye;
}
