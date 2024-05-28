package org.labonnefranquette.data.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.Produit;

import java.util.Collection;

@Data
public class Article {

    private String nom;
    private Integer quantite;
    private int prixHT;
    private Collection<Ingredient> ingredients;
    private Collection<Extra> extraSet;
}
