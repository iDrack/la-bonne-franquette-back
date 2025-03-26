package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "Ancien username vide")
    private String oldUsername;

    @NotBlank(message = "Ancien not de passe vide")
    private String oldPassword;

    @NotBlank(message = "Le username est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    @NotBlank(message = "Les roles sont requis")
    private String role;
}
