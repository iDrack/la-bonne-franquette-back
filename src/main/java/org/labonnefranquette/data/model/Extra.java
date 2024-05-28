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
@DiscriminatorValue("extra")
public class Extra extends Ingredient {

    @Column(name = "prixHT", nullable = true)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference(value = "extra-ingredient")
    private Ingredient ingredient;
}
