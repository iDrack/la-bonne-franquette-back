package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.utils.JsonConverterTools;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "commande")
@NoArgsConstructor
@AllArgsConstructor
public class Commande {

    @Column(name = "taux_tva", nullable = false)
    private final int tauxTVA = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero", nullable = false, length = 200)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private short numero;

    @Column(name = "date_saisie", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private Date dateSaisie;

    @Column(name = "date_livraison", nullable = true)
    private Date dateLivraison;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private StatusCommande status;

    @Column(name = "sur_place", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private Boolean surPlace;

    @Column(name = "nb_article", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private int nbArticle;

    @Column(name = "prix_ht", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;

    @Column(name = "articles", nullable = true, length = 1000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Article> articles;

    @Column(name = "menus", nullable = true, length = 1000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Selection> menus;

    @OneToMany(mappedBy = "commande")
    @JsonManagedReference
    @With
    private Collection<Paiement> paiementSet;

    @Column(name = "paiement_type", nullable = true, length = 5)
    private PaiementTypeCommande paiementType;

    @Override
    public String toString() {
        return "Commande{" +
                "tauxTVA=" + tauxTVA +
                ", id=" + id +
                ", numero=" + numero +
                ", dateSaisie=" + dateSaisie +
                ", dateLivraison=" + dateLivraison +
                ", status=" + status +
                ", surPlace=" + surPlace +
                ", nbArticle=" + nbArticle +
                ", prixHT=" + prixHT +
                ", articles=" + articles +
                ", menus=" + menus +
                ", paiementType=" + paiementType +
                '}';
    }
}
