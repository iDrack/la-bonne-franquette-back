package org.labonnefranquette.data.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.OrderStatus;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    @NotNull
    private Boolean dineIn;
    @NotNull
    private Date creationDate;
    @NotNull
    private Date deliveryDate;
    @NotNull
    private OrderStatus status;
    @Min(value = 0, message = "Le prix doit etre positif")
    private int totalPrice;

    private Collection<Article> articles;
    private Collection<Selection> menus;
}
