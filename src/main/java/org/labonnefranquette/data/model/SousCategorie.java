package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("sous-categorie")
@NoArgsConstructor
@AllArgsConstructor
public class SousCategorie extends Categorie {

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = true)
    @JsonBackReference
    private Categorie categorie;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long categorieId;

    @PostLoad
    private void onLoad() {
        if (categorie != null) {
            this.categorieId = categorie.getId();
        }
    }
}
