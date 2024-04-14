package org.example.labonnefranquette.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-sousCategorie")
public class SousCategorie extends Categorie {

    @ManyToOne
    @JoinColumn(name="categorie_id", nullable = false)
    private Categorie categorie;
}
