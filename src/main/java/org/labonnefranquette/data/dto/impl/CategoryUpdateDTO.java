package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryUpdateDTO {
    Long id;
    String name;
    String categoryType;
}
