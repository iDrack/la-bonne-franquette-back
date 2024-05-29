package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.dto.CommandeDTO;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
public class CommandeReadDTO implements CommandeDTO {
    private long commandeId;
    private int numero;
    private Date dateSaisie;
    private Date dateLivraison;
    private StatusCommande status;
    private Boolean surPlace;
    private int nbArticle;
    private int prixHT;
    private int tauxTVA = 10;
    private Collection<Article> articles;
    private Collection<Menu> menus;
    private Collection<Paiement> paiementSet;
    private PaiementTypeCommande paiementTypeCommande;
}
