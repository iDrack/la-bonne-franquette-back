package org.labonnefranquette.data.dto.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.dto.PaiementDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO implements PaiementDTO {
    private String type;
    private int prixPaye;
    private Boolean ticketEnvoye;
}
