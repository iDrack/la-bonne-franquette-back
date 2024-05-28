package org.labonnefranquette.data.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.labonnefranquette.data.model.entity.Article;

import java.util.Collection;

@Data
@Entity
@DiscriminatorValue("extra")
public class Extra extends Ingredient {

    @Column(name = "prixHT", nullable = true)
    private int prixHT;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @JsonBackReference(value = "extra-ingredient")
    private Ingredient ingredient;

    @ManyToMany(mappedBy = "extraSet", cascade = CascadeType.ALL)
    @JsonBackReference(value = "produit_commande-extra")
    private Collection<Article> articleSet;
}
