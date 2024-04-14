package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(schema = "lbf-ingredient")
public class Ingredient {
    @Id
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "aCuire", nullable = false, columnDefinition = "BOOLEAN")
    private boolean aCuire;

    @ManyToMany(mappedBy = "ingredientSet")
    @JsonBackReference
    private Set<Produit> produitSet;
}
