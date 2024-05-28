package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.Data;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "commande")
public class Commande {

    @Column(name = "taux_tva", nullable = false)
    private final int tauxTVA = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero", nullable = false, length = 50)
    private int numero;

    @Column(name = "date_saisie", nullable = false)
    private Date dateSaisie;

    @Column(name = "date_livraison", nullable = true)
    private Date dateLivraison;

    @Column(name = "status", nullable = false)
    private StatusCommande status;

    @Column(name = "sur_place", nullable = false)
    private Boolean surPlace;

    @Column(name = "nb_article", nullable = false)
    private int nbArticle;

    @Column(name = "prix_ht", nullable = false)
    private int prixHT;

    @Column(name = "articles", nullable = true, length = 1000)
    private Collection<Article> articles;

    @Column(name = "menus", nullable = true, length = 1000)
    private Collection<Selection> menus;

    @OneToMany(mappedBy = "commande")
    private Collection<Paiement> paiementSet;

    @Column(name = "paiement_type", nullable = true, length = 5)
    private PaiementTypeCommande paiementType;
}
