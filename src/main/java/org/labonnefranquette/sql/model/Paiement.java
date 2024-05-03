package org.labonnefranquette.sql.model;

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

    @JoinColumn(name = "commande_id", nullable = false)
    private long commandeId;

    public Paiement(String type, Boolean ticketEnvoye, int prixHT, int prixTTC, long commandeId) {
        this.date = Date.valueOf(LocalDate.now());
        this.type = type;
        this.ticketEnvoye = ticketEnvoye;
        this.prixHT = prixHT;
        this.prixTTC = prixTTC;
        this.commandeId = commandeId;
    }
}
