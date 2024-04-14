package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table (schema = "lbf_tauxTVA")
public class TauxTVA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 20)
    private String nom;

    @Column(name = "pourcentage", nullable = false, length = 5)
    private double pourcentage;

    @OneToMany(mappedBy = "tauxTVA")
    @JsonBackReference
    private Collection<Produit> produitSet;

    @OneToMany(mappedBy = "tauxTVA")
    private Collection<Extra> ExtraSet;
}
