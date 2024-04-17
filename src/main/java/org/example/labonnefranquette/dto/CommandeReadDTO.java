package org.example.labonnefranquette.dto;

import lombok.Data;
import org.example.labonnefranquette.model.Menu;
import org.example.labonnefranquette.model.Paiement;
import org.example.labonnefranquette.model.Produit;

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
    private Collection<Produit> produitSet;
    private Collection<Menu> menuSet;
    private Collection<Paiement> paiementsSet;
}
