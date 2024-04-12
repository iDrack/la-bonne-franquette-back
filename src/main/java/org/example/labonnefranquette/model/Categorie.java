package org.example.labonnefranquette.model;

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

    @ManyToMany
    @JoinTable(
            name = "categorie_possede_produit",
            joinColumns = @JoinColumn(name = "categorie_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Collection<Produit> produitSet;
}
