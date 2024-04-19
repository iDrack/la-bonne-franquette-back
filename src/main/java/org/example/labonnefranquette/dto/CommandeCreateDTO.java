package org.example.labonnefranquette.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.labonnefranquette.model.Menu;
import org.example.labonnefranquette.model.Produit;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private Boolean surPlace;
    private Collection<Produit> produitSet;
    private Collection<Menu> menuSet;
}
