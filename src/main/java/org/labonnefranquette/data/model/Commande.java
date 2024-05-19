package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private Collection<ProduitCommande> produitSet;

    @ManyToMany
    @JoinTable(
            name = "commande_contient_menu",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private Collection<Menu> menuSet;

    @OneToMany(mappedBy = "commande")
    private Collection<Paiement> paiementSet;


}
