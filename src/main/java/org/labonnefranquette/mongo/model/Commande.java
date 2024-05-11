package org.labonnefranquette.mongo.model;

import com.google.common.hash.HashCode;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document("commandes")
public class Commande {

    //on map _id du document Mongo avec commandeId de notre objet
    //TODO supprimmer toutes les instances de setter de commandeID afin de laisser gérer mongo
    @Id
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

        UUID tempId = new UUID(HashCode.fromLong(dateSaisie.getTime()).asLong(), HashCode.fromLong(dateSaisie.getTime() + numero).asLong());
        this.commandeId = tempId.getMostSignificantBits();
    }

    public Commande() {
    }

    public int getPrixTTC() {
        return this.prixHT * this.tauxTVA;
    }

    public void setCommandeId() {
    }
}
