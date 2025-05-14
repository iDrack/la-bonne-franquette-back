package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAllByStatus(OrderStatus status, String token);

    Order getById(long id) throws NullPointerException;
    Order create(Order order, String token);
    Boolean delete(Long id);
    Order addPayment(Order order, Payment payment);
    Order updateStatus(Long id) throws RuntimeException;

    Order patch(Long id, Map<String, Object> updates);
    List<OrderListProjection> getAllByDate(Date date);
}