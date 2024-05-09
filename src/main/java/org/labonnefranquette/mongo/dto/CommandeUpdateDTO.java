package org.labonnefranquette.mongo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.labonnefranquette.mongo.model.StatusCommande;

import java.util.Collection;
import java.util.List;

public class CommandeUpdateDTO implements CommandeDTO {
    private Long commandeId;
    private int numero;
    private Boolean surPlace;
    private Collection<Long> menuSet;
    private StatusCommande status;
    @JsonProperty("produitsAvecExtras")
    private List<List<Long>> produitsAvecExtras;
    private int prixHT;
}
