package org.labonnefranquette.data.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Set;

@Data
@Entity
@DiscriminatorValue("extra")
@NoArgsConstructor
@AllArgsConstructor
public class Extra extends Ingredient {

    @Column(name = "prixHT", nullable = true)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference(value = "extra-ingredient")
    @With
    private Ingredient ingredient;

    @ManyToMany(mappedBy = "extraSet")
    @JsonBackReference(value = "produit-extra")
    @With
    private Set<Produit> produitSet;

}
