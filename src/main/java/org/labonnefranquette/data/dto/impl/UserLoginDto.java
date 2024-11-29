package org.labonnefranquette.data.dto.impl;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserLoginDto  {
    @NotBlank(message = "Le username est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;
}
