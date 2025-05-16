package org.labonnefranquette.data.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Ingredient;

import java.util.Collection;

@Data
@Embeddable
public class Article {

    private String name;
    private Integer quantity;
    private int totalPrice;
    private Collection<Ingredient> ingredients;
    private Collection<Addon> addons;
    private boolean modified;
}
