package org.example.labonnefranquette.model;

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
@Table(schema = "lbf-ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "aCuire", nullable = false, columnDefinition = "BOOLEAN")
    private boolean aCuire;

    @OneToOne(mappedBy = "ingredient")
    @JsonBackReference
    private Extra extra;

    @ManyToMany(mappedBy = "ingredientSet")
    @JsonBackReference
    private Set<Produit> produitSet;
}
