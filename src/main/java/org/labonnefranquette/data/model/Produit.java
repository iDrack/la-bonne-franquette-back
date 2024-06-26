package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.entity.Article;

import java.util.Collection;

@Data
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prix_ht", nullable = false, length = 10)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_appartient_categorie",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Collection<Categorie> categorieSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_contient_ingredient",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Collection<Ingredient> ingredientSet;

    @ManyToMany(mappedBy = "produitSet", fetch = FetchType.EAGER)
    @JsonBackReference(value = "menu-produit")
    private Collection<Menu> menuSet;

}
