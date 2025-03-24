package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto  {
    @NotBlank(message = "Le username est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    private Long restaurantId;

    private String role;

    public UserCreateDto(@NotBlank(message = "Nom du responsable est obligatoire.") String username, @NotBlank(message = "Mot de passe du responsable est obligatoire.") String password, Long id) {
        this.username = username;
        this.password = password;
        this.restaurantId = id;
        this.role = "ROLE_USER";
    }

    @Override
    public String toString() {
        return "user=" + this.username + ".";
    }
}
