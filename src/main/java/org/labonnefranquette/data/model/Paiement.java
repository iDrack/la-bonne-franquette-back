package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

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
    @NotNull(message = "Ce champs ne peut pas être vide")
    private PaiementTypeCommande type;

    @Column(name = "ticket_envoye", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private Boolean ticketEnvoye;

    @Column(name = "prix_ht", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @Column(name = "prix_ttc", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixTTC;

    @ManyToOne
    @JoinColumn(name = "commande")
    @JsonBackReference
    private Commande commande;

    public Paiement(PaiementTypeCommande type, Boolean ticketEnvoye, int prixHT, int prixTTC, Commande commande) {
        this.date = Date.valueOf(LocalDate.now());
        this.type = type;
        this.ticketEnvoye = ticketEnvoye;
        this.prixHT = prixHT;
        this.prixTTC = prixTTC;
        this.commande = commande;
    }
}
