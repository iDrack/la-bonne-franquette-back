package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;
import org.labonnefranquette.data.utils.JsonConverterTools;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment extends HasRestaurantAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date = new Date();

    @NotNull(message = "Ce champs ne peut pas être vide")
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private int price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Column(name = "articles", nullable = true, length = 5000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Article> articles;

    @Column(name = "selections", nullable = true, length = 5000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Selection> selections;

    public Payment(String type, int price, Order order, Collection<Article> articles, Collection<Selection> selections) {
        this.type = type;
        this.price = price;
        this.order = order;
        this.articles = articles;
        this.selections = selections;
        this.setRestaurant(order.getRestaurant());
    }


    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", prixHT=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return price == payment.price && type == payment.type && Objects.equals(order, payment.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price, order);
    }
}
