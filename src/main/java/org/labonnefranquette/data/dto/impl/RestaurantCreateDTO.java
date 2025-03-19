package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantCreateDTO {

    @NotBlank(message = "Nom du restaurant est obligatoire.")
    private String restaurantName;

    @NotBlank(message = "Nom du responsable est obligatoire.")
    private String username;

    @NotBlank(message = "Mot de passe du responsable est obligatoire.")
    private String password;
}
