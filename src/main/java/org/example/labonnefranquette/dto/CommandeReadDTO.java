package org.example.labonnefranquette.dto;

import lombok.Data;

import java.sql.Date;
import java.util.Collection;

@Data
public class CommandeReadDTO implements CommandeDTO {
    private Long id;
    private int numero;
    private Date dateSaisie;
    private Date dateLivraison;
    private String status;
    private Boolean surPlace;
    private int prixHT;
    private Collection<Long> produitIdSet;
    private Collection<Long> menuIdSet;
    private Collection<Long> paiementsIdSet;
}
