package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(schema = "lbf_produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prixHT", nullable = false, length = 10)
    private int prixHT;

    @ManyToOne
    @JoinColumn(name = "tauxtva_id", nullable = false)
    private TauxTVA tauxTVA;

    @ManyToMany
    @JoinTable(
            name = "produit_appartient_categorie",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Set<Categorie> categorieSet;

    @ManyToMany
    @JoinTable(
            name = "produit_contient_ingredient",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredientSet;
}
