package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeCreateDTO  {
    @NotNull
    private Boolean surPlace;
    @NotNull
    private Date dateSaisie;
    @NotNull
    private Date dateLivraison;
    @NotNull
    private StatusCommande status;
    @Min(value = 0, message = "Le prix doit etre positif")
    private int prixTTC;

    private Collection<Article> articles;
    private Collection<Selection> menus;
}
