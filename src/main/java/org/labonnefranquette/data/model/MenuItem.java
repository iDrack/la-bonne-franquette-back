package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.RestaurantItemAbs;

import java.util.List;

@Data
@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem extends RestaurantItemAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "optional", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private boolean optional;

    @ManyToOne
    @JoinColumn(name = "menu")
    @JsonBackReference
    @With
    private Menu menu;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_item_contains_product",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id",
                    nullable = true,
                    foreignKey = @ForeignKey(
                            name = "FK_menu_item_contains_product_product",
                            foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL"
                    )
            )
    )
    private List<Product> products;

    public String getName() {
        return String.valueOf(this.id);
    }
}
