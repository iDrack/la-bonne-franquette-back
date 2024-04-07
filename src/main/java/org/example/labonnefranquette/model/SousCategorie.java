package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-sousCategorie")
public class SousCategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @ManyToOne
    @JoinColumn(name="categorie_id", nullable = false)
    private Categorie categorie;
}
