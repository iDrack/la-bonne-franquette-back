package org.example.labonnefranquette.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.labonnefranquette.model.Menu;
import org.example.labonnefranquette.model.Paiement;
import org.example.labonnefranquette.model.Produit;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private String status;
    private Boolean surPlace;
    private Collection<Produit> produitSet;
    private Collection<Menu> menuSet;
    private Collection<Paiement> paiementsSet;
}
