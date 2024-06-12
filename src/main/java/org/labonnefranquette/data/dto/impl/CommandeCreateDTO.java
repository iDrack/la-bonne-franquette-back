package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.labonnefranquette.data.dto.CommandeDTO;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private int numero;
    private Boolean surPlace;
    private Collection<Selection> menus;
    private Collection<Paiement> paiementSet;
    private StatusCommande status;
    private Collection<Article> articles;
    private int prixHT;
}
