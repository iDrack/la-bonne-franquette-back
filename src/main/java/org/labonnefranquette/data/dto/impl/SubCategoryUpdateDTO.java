package org.labonnefranquette.data.dto.impl;

import lombok.Getter;
import lombok.Setter;
import org.labonnefranquette.data.model.Category;

@Getter
@Setter
public class SubCategoryUpdateDTO extends CategoryUpdateDTO {

    Category category;

    public SubCategoryUpdateDTO(Long id, String name, String categoryType, Category category) {
        super(id, name, categoryType);
        this.category = category;
    }
}
