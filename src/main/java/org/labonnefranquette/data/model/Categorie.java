package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.Collection;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categorie_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("categorie")
@Table(name = "categorie")
@NoArgsConstructor
@AllArgsConstructor
public class Categorie implements HasRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String nom;

    // @OneToMany(mappedBy = "categorie", fetch = FetchType.EAGER)
    // private Collection<SousCategorie> sousCategorieSet;

    @ManyToMany(mappedBy = "categorieSet")
    @JsonBackReference(value = "categorie-produit")
    @With
    private Collection<Produit> produitSet;

    @Column(name = "categorie_type", insertable = false, updatable = false)
    @With
    private String categorieType;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;
}
