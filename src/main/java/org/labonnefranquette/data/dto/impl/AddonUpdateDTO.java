package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.enums.VATRate;

@Data
public class AddonUpdateDTO {
    Long id;
    String name;
    VATRate VATRate;
    int price;
}
