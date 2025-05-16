package org.labonnefranquette.data.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class Selection {

    private String name;
    private List<Article> articles;
    private Integer quantity;
    private int totalPrice;
    private boolean modified;
}
