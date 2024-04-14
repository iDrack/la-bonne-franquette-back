package org.example.labonnefranquette.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "tauxtva_id", nullable = false)
    @JsonBackReference
    private TauxTVA tauxTVA;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference
    private Ingredient ingredient;

}
