package org.labonnefranquette.data.dto.impl;

import lombok.Getter;
import lombok.Setter;
import org.labonnefranquette.data.model.Category;

@Getter
@Setter
public class SubCategoryCreateDTO extends CategoryCreateDTO {
    Category category;

    public SubCategoryCreateDTO(String name, String categoryType, Category category) {
        super(name, categoryType);
        this.category = category;
    }
}
