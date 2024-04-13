package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(schema = "lbf-categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 20)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private Set<SousCategorie> sousCategorieSet;

    @ManyToMany(mappedBy = "categorieSet")
    @JsonBackReference
    private Collection<Produit> produitSet;
}
