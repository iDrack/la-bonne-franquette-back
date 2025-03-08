package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;
import org.labonnefranquette.data.utils.JsonConverterTools;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paiement")
public class Paiement extends HasRestaurantAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @NotNull(message = "Ce champs ne peut pas être vide")
    @JoinColumn(name = "type_id", nullable = false)
    private PaiementType type;

    @Column(name = "prix_ht", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prix;

    @ManyToOne
    @JoinColumn(name = "commande")
    @JsonBackReference
    private Commande commande;

    @Column(name = "articles", nullable = true, length = 1000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Article> articles;

    public Paiement(PaiementType type, int prix, Commande commande, Collection<Article> articles) {
        this.date = new Date();
        this.type = type;
        this.prix = prix;
        this.commande = commande;
        this.articles = articles;
        this.setRestaurant(commande.getRestaurant());
    }


    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type.getName() +
                ", prixHT=" + prix +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
        return prix == paiement.prix && type == paiement.type && Objects.equals(commande, paiement.commande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, prix, commande);
    }
}
