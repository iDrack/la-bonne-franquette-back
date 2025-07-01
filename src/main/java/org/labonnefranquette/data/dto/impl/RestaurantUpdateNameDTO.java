package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantUpdateNameDTO {
    @NotBlank(message = "Nom du restaurant est obligatoire.")
    private String newRestaurantName;
}
