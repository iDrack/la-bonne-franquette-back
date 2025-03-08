package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Collection;
import java.util.Date;

@Data
public class CommandeReadDTO  {
    private long commandeId;
    private short numero;
    private Date dateSaisie;
    private Date dateLivraison;
    private StatusCommande status;
    private Boolean surPlace;
    private int nbArticle;
    private int prixTTC;
    private Collection<Article> articles;
    private Collection<Selection> menus;
    private Collection<Paiement> paiementSet;
    private String paiementTypeCommande;
    private boolean isModified;

}
