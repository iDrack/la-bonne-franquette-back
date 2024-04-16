package org.example.labonnefranquette.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("sous-categorie")
@Table(schema = "lbf-sousCategorie")
public class SousCategorie extends Categorie {
    @ManyToOne
    @JoinColumn(name="categorie_id", nullable = false)
    @JsonBackReference
    private Categorie categorie;
}
