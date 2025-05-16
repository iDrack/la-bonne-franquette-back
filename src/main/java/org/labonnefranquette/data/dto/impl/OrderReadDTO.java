package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.OrderStatus;

import java.util.Collection;
import java.util.Date;

@Data
public class OrderReadDTO {
    private long id;
    private short number;
    private Date creationDate;
    private Date deliveryDate;
    private OrderStatus status;
    private Boolean dineIn;
    private int totalItems;
    private int totalPrice;
    private Collection<Article> articles;
    private Collection<Selection> menus;
    private Collection<Payment> payments;
    private String paymentType;
    private boolean paid;

}
