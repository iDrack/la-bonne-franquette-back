package org.example.labonnefranquette.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-extras")
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 20)
    private String nom;

    @Column(name = "prixHT", nullable = false)
    private int prixHT;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

}
