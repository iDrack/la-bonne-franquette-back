package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;

import java.util.Set;

@Data
@Entity
@Table(name = "ingredient")
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient extends HasRestaurantAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String nom;

    @ManyToMany(mappedBy = "ingredientSet")
    @JsonBackReference(value = "produit-ingredient")
    @With
    private Set<Produit> produitSet;
}
