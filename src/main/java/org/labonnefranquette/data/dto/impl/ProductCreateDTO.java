package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.enums.VATRate;

import java.util.Collection;

@Data
public class ProductCreateDTO {
    String name;
    VATRate VATRate;
    int price;
    Collection<Addon> addons;
    Collection<Category> categories;
    Collection<Ingredient> ingredients;
}
