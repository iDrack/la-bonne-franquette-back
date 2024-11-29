package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserCreateDto  {
    @NotBlank(message = "Le username est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    @Override
    public String toString() {
        return "user=" + this.username + ".";
    }
}
