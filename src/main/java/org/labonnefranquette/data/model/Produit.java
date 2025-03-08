package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.RestaurantItemAbs;

import java.util.Collection;

@Data
@Entity
@Table(name = "produit")
@NoArgsConstructor
@AllArgsConstructor
public class Produit extends RestaurantItemAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_appartient_categorie",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    @With
    private Collection<Categorie> categorieSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_contient_ingredient",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @With
    private Collection<Ingredient> ingredientSet;

    @JsonManagedReference(value = "produit-extra")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_contient_extra",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_id")
    )
    @With
    private Collection<Extra> extraSet;
}
