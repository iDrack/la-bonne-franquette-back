package org.labonnefranquette.mongo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class CommandeCreateDTO implements CommandeDTO {
    private int numero;
    private Boolean surPlace;
    private Collection<Long> menuSet;
    @JsonProperty("produitsAvecExtras")
    private List<List<Long>> produitsAvecExtras;
    private int prixHT;
}
