package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.enums.TauxTVA;

import java.util.Collection;

@Data
public class ProductCreateDTO {
    String name;
    TauxTVA tauxTVA;
    int prixHT;
    Collection<Addon> addons;
    Collection<Category> categories;
    Collection<Ingredient> ingredients;
}
