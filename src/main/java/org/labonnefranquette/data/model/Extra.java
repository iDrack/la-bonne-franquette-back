package org.labonnefranquette.data.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "extra")
@NoArgsConstructor
@AllArgsConstructor
public class Extra extends RestaurantItemAbs {
    //TODO dégager la relation ingrédient - extra, faire une table extra
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false, length = 50)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String nom;

    @ManyToMany(mappedBy = "extraSet")
    @JsonBackReference(value = "produit-extra")
    @With
    private Set<Produit> produitSet;

}
