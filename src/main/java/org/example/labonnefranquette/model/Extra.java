package org.example.labonnefranquette.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("extra")
@Table(name = "extra")
public class Extra extends Ingredient {

    @Column(name = "prixHT", nullable = true)
    private int prixHT;

    @ManyToOne
    @JoinColumn(name = "taux_tva_id", nullable = true)
    @JsonBackReference
    private TauxTVA tauxTVA;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference
    private Ingredient ingredient;

}
