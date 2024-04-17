package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prix_ht", nullable = false, length = 10)
    private int prixHT;

    @ManyToMany
    @JoinTable(
            name = "produit_appartient_categorie",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Collection<Categorie> categorieSet;

    @ManyToMany
    @JoinTable(
            name = "produit_contient_ingredient",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Collection<Ingredient> ingredientSet;

    @ManyToMany(mappedBy = "produitSet")
    @JsonBackReference
    private Collection<Menu> menuSet;

    @ManyToMany(mappedBy = "produitSet")
    @JsonBackReference
    private Collection<Commande> commandeSet;
}
