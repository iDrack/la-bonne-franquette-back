package org.labonnefranquette.sql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ingredient_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ingredient")
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "a_cuire", nullable = false, columnDefinition = "boolean default false")
    private boolean aCuire;

    @OneToOne(mappedBy = "ingredient")
    @JsonBackReference(value = "ingredient-extra")
    private Extra extra;

    @ManyToMany(mappedBy = "ingredientSet")
    @JsonBackReference(value = "produit-ingredient")
    private Set<Produit> produitSet;
}
