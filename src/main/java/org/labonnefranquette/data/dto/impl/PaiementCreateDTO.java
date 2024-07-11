package org.labonnefranquette.data.dto.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.dto.PaiementDTO;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO implements PaiementDTO {
    private PaiementTypeCommande type;
    private int prixPaye;
    private Boolean ticketEnvoye;
}
