package org.labonnefranquette.sql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categorie_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("categorie")
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private Collection<SousCategorie> sousCategorieSet;

    @ManyToMany(mappedBy = "categorieSet")
    @JsonBackReference(value = "categorie-produit")
    private Collection<Produit> produitSet;
}
