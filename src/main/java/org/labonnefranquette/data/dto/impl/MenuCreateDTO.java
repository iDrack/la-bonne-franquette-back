package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.model.enums.TauxTVA;

import java.util.Collection;

@Data
public class MenuCreateDTO {
    String name;
    TauxTVA tauxTVA;
    int prixHT;
    Collection<MenuItem> menuItems;
}
