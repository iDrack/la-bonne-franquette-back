package org.labonnefranquette.data.dto.impl;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PaymentCreateDTO {
    @NotNull
    private String type;
    private int price;
    private Collection<Article> articles;
    private Collection<Selection> selections;
}
