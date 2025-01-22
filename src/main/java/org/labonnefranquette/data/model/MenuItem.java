package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@Entity
@Table(name = "menu_item")
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "optional", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private boolean optional;

    @Column(name = "extra_price", length = 10)
    private Integer extraPriceHT;

    @ManyToOne
    @JoinColumn(name = "menu")
    @JsonBackReference
    @With
    private Menu menu;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_item_contient_produit",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    @JsonIgnoreProperties({"categorieSet", "ingredientSet"})
    @With
    private List<Produit> produitSet;
}
