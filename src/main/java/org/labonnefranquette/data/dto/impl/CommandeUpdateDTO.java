package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.dto.CommandeDTO;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.enums.StatusCommande;

import java.util.Collection;
import java.util.List;

@Data
public class CommandeUpdateDTO implements CommandeDTO {
    private long commandeId;
    private int numero;
    private Boolean surPlace;
    private Collection<Long> menuSet;
    private StatusCommande status;
    private List<Article> produiutSet;
    private int prixHT;
}
