package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.model.enums.VATRate;

import java.util.Collection;

@Data
public class MenuUpdateDTO {
    Long id;
    String name;
    VATRate VATRate;
    int price;
    Collection<MenuItem> menuItems;
}
