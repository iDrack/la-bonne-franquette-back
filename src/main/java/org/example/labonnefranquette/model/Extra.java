package org.example.labonnefranquette.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("extra")
public class Extra extends Ingredient {

    @Column(name = "prixHT", nullable = true)
    private int prixHT;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference(value = "extra-ingredient")
    private Ingredient ingredient;

}
