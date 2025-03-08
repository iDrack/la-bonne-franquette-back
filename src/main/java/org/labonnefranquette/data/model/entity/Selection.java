package org.labonnefranquette.data.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class Selection {

    private String nom;
    private List<Article> articles;
    private Integer quantite;
    private int prixTTC;
    private boolean isModified;
}
