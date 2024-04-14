package org.example.labonnefranquette.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-extras")
public class Extra extends Ingredient {

    @Column(name = "typeTVA", nullable = false)
    private int typeTVA;

    @Column(name = "prixHT", nullable = false)
    private int prixHT;
}
