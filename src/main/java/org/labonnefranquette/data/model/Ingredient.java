package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String nom;

    @Column(name = "a_cuire", nullable = false, columnDefinition = "boolean default false")
    private boolean aCuire;

    @OneToOne(mappedBy = "ingredient")
    @JsonBackReference(value = "ingredient-extra")
    private Extra extra;

    @ManyToMany(mappedBy = "ingredientSet")
    @JsonBackReference(value = "produit-ingredient")
    private Set<Produit> produitSet;

    @Column(name = "ingredient_type", insertable = false, updatable = false)
    @JsonIgnore
    private String ingredientType;
}
