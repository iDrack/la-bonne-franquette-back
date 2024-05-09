package org.labonnefranquette.sql.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO implements PaiementDTO {
    private String type;
    private int prixPaye;
    private Boolean ticketEnvoye;
}
