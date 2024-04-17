package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("sous-categorie")
@Table(name = "sousCategorie")
public class SousCategorie extends Categorie {
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = true)
    @JsonBackReference
    private Categorie categorie;
}
