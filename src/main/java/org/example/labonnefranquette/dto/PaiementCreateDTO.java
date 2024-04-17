package org.example.labonnefranquette.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO implements PaiementDTO {
    private String type;
    private Boolean ticketEnvoye;
}
