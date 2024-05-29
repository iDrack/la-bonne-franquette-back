package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String nom;

    @Column(name = "prix_ht", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @ManyToMany
    @JoinTable(
            name = "menu_contient_produit",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Collection<Produit> produitSet;
}
