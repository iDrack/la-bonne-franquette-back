package org.labonnefranquette.data.projection;

import org.labonnefranquette.data.model.enums.OrderStatus;

import java.util.Date;

public interface OrderListProjection {
    long getId();
    short getNumber();
    Date getCreationDate();
    Date getDeliveryDate();
    int getTotalPrice();
    Boolean getDineIn();
    OrderStatus getStatus();
    String getPaymentType();
    boolean getPaid();
}
