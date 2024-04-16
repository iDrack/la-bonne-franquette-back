package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prix_ht", nullable = false, length = 50)
    private int prixHT;

    @ManyToOne
    @JoinColumn(name = "taux_tva_id", nullable = false)
    private TauxTVA tauxTVA;

    @ManyToMany
    @JoinTable(
            name = "menu_contient_produit",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Collection<Produit> produitSet;
}
