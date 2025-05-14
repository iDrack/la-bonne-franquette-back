package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.model.interfaces.HasPrice;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;
import org.labonnefranquette.data.utils.JsonConverterTools;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_creation_date", columnList = "creation_date")
})
@NoArgsConstructor
@AllArgsConstructor
public class Order extends HasRestaurantAbs implements HasPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number", nullable = false, length = 200)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private short number;

    @Column(name = "creation_date", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private Date creationDate;

    @Column(name = "delivery_date", nullable = true)
    private Date deliveryDate;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private OrderStatus status;

    @Column(name = "dine_in", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private Boolean dineIn;

    @Column(name = "total_items", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private int totalItems;

    @Column(name = "articles", nullable = true, length = 1000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Article> articles;

    @Column(name = "menus", nullable = true, length = 1000)
    @Convert(converter = JsonConverterTools.class)
    @With
    private Collection<Selection> menus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @With
    private Collection<Payment> payments;

    @Column(name = "payment_type", nullable = true, length = 50)
    private String paymentType;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "paid", nullable = false)
    private boolean paid = false;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number=" + number +
                ", creationDate=" + creationDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", dineIn=" + dineIn +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                ", articles=" + articles +
                ", menus=" + menus +
                ", paymentType='" + paymentType + '\'' +
                ", paid=" + paid +
                ", payments=" + payments +
                '}';
    }
}
