package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.labonnefranquette.data.model.*;

import java.util.List;

@Data
public class RestaurantLoadDTO {
    @NotBlank(message = "Les ingrédients sont requis")
    private List<Ingredient> ingredients;

    @NotBlank(message = "Les extras sont requis")
    private List<Addon> addons;

    @NotBlank(message = "Les catégories sont requis")
    private List<Category> categories;

    @NotBlank(message = "Les produits sont requis")
    private List<Product> products;

    @NotBlank(message = "Les menus sont requis")
    private List<Menu> menus;
}
