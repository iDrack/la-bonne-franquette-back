package org.labonnefranquette.mongo.dto;

import lombok.Data;
import org.labonnefranquette.mongo.model.StatusCommande;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
public class CommandeReadDTO implements CommandeDTO {
    private int numero;
    private Date dateSaisie;
    private Date dateLivraison;
    private StatusCommande status;
    private Boolean surPlace;
    private int nbArticle;
    private int prixHT;
    private int tauxTVA = 10;
    private List<List<Long>> produitsAvecExtras;
    //Collection<menuId>
    private Collection<Long> menuSet;
    //Collection<paiementId>
    private Collection<Long> paiementSet;
}
