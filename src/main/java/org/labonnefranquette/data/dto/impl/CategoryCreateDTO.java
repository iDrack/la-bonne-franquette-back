package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryCreateDTO {
    String name;
    String categoryType;
}
