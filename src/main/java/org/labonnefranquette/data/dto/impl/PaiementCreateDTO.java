package org.labonnefranquette.data.dto.impl;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PaiementCreateDTO  {
    @NotNull
    private String type;
    @Min(value = 0, message = "Prix HT doit être positif")
    private int prix;
    private Collection<Article> articles;
    private Collection<Selection> selections;
}
