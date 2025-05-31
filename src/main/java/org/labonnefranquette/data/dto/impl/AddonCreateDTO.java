package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.enums.TauxTVA;

@Data
public class AddonCreateDTO {
    String name;
    Ingredient ingredient;
    TauxTVA tauxTVA;
    int prixHT;
}
