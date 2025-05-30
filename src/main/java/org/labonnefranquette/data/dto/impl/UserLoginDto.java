package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto  {
    @NotBlank(message = "Le username est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;
}
