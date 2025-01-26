package org.labonnefranquette.data.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.model.Ingredient;

import java.util.Collection;

@Data
@Embeddable
public class Article {

    private String nom;
    private Integer quantite;
    private int prixHT;
    private Collection<Ingredient> ingredients;
    private Collection<Extra> extraSet;
    private boolean isModified;
}
