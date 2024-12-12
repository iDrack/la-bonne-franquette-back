package org.labonnefranquette.data.dto.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementCreateDTO  {
    @NotNull
    private PaiementTypeCommande type;
    @Min(value = 0, message = "Prix TTC doit être positif")
    private int prixTTC;
    @NotNull
    private Boolean ticketEnvoye;
}
