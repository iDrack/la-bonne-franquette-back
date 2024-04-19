package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "date_saisie", nullable = false)
    private Date dateSaisie;

    @Column(name = "date_livraison")
    private Date dateLivraison;

    @Column(name = "status", length = 10, nullable = false)
    private StatusCommande status;

    @Column(name = "sur_place")
    private Boolean surPlace;

    @Column(name = "nb_article", nullable = false)
    private int nbArticle;

    @Column(name = "prix_ht", nullable = false)
    private int prixHT;

    @JoinColumn(name = "taux_tva", nullable = false)
    private int tauxTVA = 10;

    @ManyToMany
    @JoinTable(
            name = "commande_possede_produit",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Collection<Produit> produitSet;

    @ManyToMany
    @JoinTable(
            name = "commande_possede_menu",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private Collection<Menu> menuSet;

    @OneToMany(mappedBy = "commande")
    private Collection<Paiement> paiementSet;

    public Commande(int numero, Boolean surPlace, int prixHT, Collection<Produit> produitSet, Collection<Menu> menuSet) {
        this.numero = numero;
        this.dateSaisie = Date.valueOf(LocalDate.now());
        this.dateLivraison = null;
        this.status = StatusCommande.EN_COURS;
        this.surPlace = surPlace;
        this.nbArticle = produitSet.size();
        this.prixHT = prixHT;
        this.tauxTVA = 10;
        this.produitSet = produitSet;
        this.menuSet = menuSet;
        this.paiementSet = null;
    }
}
