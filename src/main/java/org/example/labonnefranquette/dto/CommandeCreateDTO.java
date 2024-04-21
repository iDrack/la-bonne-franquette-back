package org.example.labonnefranquette.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private int numero;
    private Boolean surPlace;
    private Collection<Long> menuSet;
    private Map<Long, List<Long>> produitsAndExtras;
}
