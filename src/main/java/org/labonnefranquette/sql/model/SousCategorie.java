package org.labonnefranquette.sql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("sous-categorie")
public class SousCategorie extends Categorie {
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = true)
    @JsonBackReference(value = "categorie-sous-categorie")
    private Categorie categorie;
}
