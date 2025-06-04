package org.labonnefranquette.data.model;

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
@Table(name = "menus")
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends RestaurantItemAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String name;

    @OneToMany(
            mappedBy = "menu",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @With
    private List<MenuItem> menuItems;

}
