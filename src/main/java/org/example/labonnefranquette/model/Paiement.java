package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "type", nullable = false, length = 3)
    private String type;

    @Column(name = "ticket_envoye", nullable = false)
    private Boolean ticketEnvoye;

    @Column(name = "prix_ht", nullable = false)
    private int prixHT;

    @Column(name = "prix_ttc", nullable = false)
    private int prixTTC;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    public Paiement(String type, Boolean ticketEnvoye, int prixHT, int prixTTC, Commande commande) {
        this.date = Date.valueOf(LocalDate.now());
        this.type = type;
        this.ticketEnvoye = ticketEnvoye;
        this.prixHT = prixHT;
        this.prixTTC = prixTTC;
        this.commande = commande;
    }
}
