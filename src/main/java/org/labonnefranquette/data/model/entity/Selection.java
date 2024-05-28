package org.labonnefranquette.data.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.model.Produit;

import java.util.Collection;
import java.util.List;

@Data
@Embeddable
public class Selection {

    private String nom;
    private List<Article> articles;
    private Integer quantite;
    private int prixHT;
}
