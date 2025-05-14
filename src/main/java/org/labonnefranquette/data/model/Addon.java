package org.labonnefranquette.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.interfaces.RestaurantItemAbs;

import java.util.Set;

@Data
@Entity
@Table(name = "addons")
@NoArgsConstructor
@AllArgsConstructor
public class Addon extends RestaurantItemAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String name;

    @ManyToMany(mappedBy = "addons")
    @JsonIgnore
    @With
    private Set<Product> products;
}
