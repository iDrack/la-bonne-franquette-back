package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categorie_type",
        discriminatorType = DiscriminatorType.STRING)
@Table(schema = "lbf-categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private Set<SousCategorie> sousCategorieSet;

    @ManyToMany(mappedBy = "categorieSet")
    @JsonBackReference
    private Set<Produit> produitSet;
}
