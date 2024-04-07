package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (schema = "lbf_tauxTVA")
public class TauxTVA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 20)
    private String nom;

    @Column(name = "poucentage", nullable = false, length = 5)
    private double pourcentage;
}
