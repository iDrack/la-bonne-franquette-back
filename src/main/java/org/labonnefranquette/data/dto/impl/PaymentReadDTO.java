package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.model.entity.Article;

import java.sql.Date;
import java.util.Collection;

@Data
public class PaymentReadDTO {
    private long id;
    private long orderId;
    private Date date;
    private PaymentType type;
    private int price;
    private Collection<Article> articles;
}