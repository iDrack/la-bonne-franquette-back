package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "produit_commande")
public class ProduitCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "produit")
    private Produit produit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commande")
    private Commande commande;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "produit_commande_contient_extra",
            joinColumns = @JoinColumn(name = "produit_commande_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_id")
    )
    private Collection<Extra> extraSet;
}
