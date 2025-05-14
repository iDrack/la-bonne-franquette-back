package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;

import java.util.Collection;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("category")
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category extends HasRestaurantAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference(value = "category-product")
    @With
    private Collection<Product> products;

    @Column(name = "category_type", insertable = false, updatable = false)
    @With
    private String categoryType;
}
