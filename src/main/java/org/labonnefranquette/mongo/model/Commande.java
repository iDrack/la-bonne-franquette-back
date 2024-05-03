package org.labonnefranquette.mongo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Document("commandes")
public class Commande {

    private static long counter = 0;

    private long commandeId;

    private int numero;

    private Date dateSaisie;

    private Date dateLivraison;

    private StatusCommande status;

    private Boolean surPlace;

    private int nbArticle;

    private int prixHT;

    private int tauxTVA = 10;

    //Ou le premeier Long correspond à l'id du produit, la suit seront les ids des extras à appliquer
    private List<List<Long>> produitsAvecExtras;

    //Collection<menuId>
    private Collection<Long> menuSet;

    //Collection<paiementId>
    private Collection<Long> paiementSet;

    public Commande(int numero, Boolean surPlace, int prixHT, List<List<Long>> produitsAvecExtras, Collection<Long> menuSet) {
        this.commandeId = counter++;
        this.numero = numero;
        this.dateSaisie = new Date();
        this.dateLivraison = null;
        this.status = StatusCommande.EN_COURS;
        this.surPlace = surPlace;
        this.nbArticle = produitsAvecExtras.size();
        this.prixHT = prixHT;
        this.tauxTVA = 10;
        this.produitsAvecExtras = produitsAvecExtras;
        this.menuSet = menuSet;
        this.paiementSet = null;
    }

    public Commande() {
        this.commandeId = counter++;
    }

    public int getPrixTTC() {
        return this.prixHT * this.tauxTVA;
    }
}
