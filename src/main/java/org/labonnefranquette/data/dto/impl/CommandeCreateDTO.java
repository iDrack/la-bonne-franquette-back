package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.labonnefranquette.data.dto.CommandeDTO;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.ProduitCommande;
import org.labonnefranquette.data.model.StatusCommande;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private int numero;
    private Boolean surPlace;
    private Collection<Menu> menuSet;
    private Collection<Paiement> paiementSet;
    private StatusCommande status;
    private List<ProduitCommande> produitSet;
    private int prixHT;
}
