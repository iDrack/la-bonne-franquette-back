package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "aCuire", nullable = false)
    private boolean aCuire;

    @Column(name = "typeTVA", nullable = false)
    private int typeTVA;

    @OneToOne(mappedBy = "ingredient")
    private Extra extra;
}
