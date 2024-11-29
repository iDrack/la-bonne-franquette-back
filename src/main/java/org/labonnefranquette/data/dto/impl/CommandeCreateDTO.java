package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.StatusCommande;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Min;
import java.util.Collection;

@Data
@AllArgsConstructor
public class CommandeCreateDTO  {
    @NotNull
    private Boolean surPlace;
    private Collection<Selection> menus;
    private Collection<Paiement> paiementSet;
    @NotNull
    private StatusCommande status;
    @NotEmpty
    private Collection<Article> articles;
    @Min(value = 0, message = "Le prix doit etre positif")
    private int prixHT;
}
