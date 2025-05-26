package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.interfaces.RestaurantItemAbs;

import java.util.Collection;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends RestaurantItemAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_in_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(
                            name = "FK_product_in_category_category",
                            foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE"
                    )
            )
    )
    private Collection<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_contains_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "ingredient_id",
                    foreignKey = @ForeignKey(
                            name = "FK_product_contains_ingredient_ingredient",
                            foreignKeyDefinition = "FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE"
                    )
            )
    )
    private Collection<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_contains_addon",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "addon_id",
                    foreignKey = @ForeignKey(
                            name = "FK_product_contains_addon_addon",
                            foreignKeyDefinition = "FOREIGN KEY (addon_id) REFERENCES addons(id) ON DELETE CASCADE"
                    )
            )
    )
    private Collection<Addon> addons;
}
