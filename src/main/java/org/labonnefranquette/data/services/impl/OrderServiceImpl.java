package org.labonnefranquette.data.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;
import org.labonnefranquette.data.repository.OrderRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.OrderService;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.utils.OrderTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderTools orderTools;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public List<Order> getAllByStatus(OrderStatus status, String token) {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        return orderRepository.findAllByStatus(status).stream().filter((x) -> Objects.equals(x.getRestaurant().getId(), idRestaurant)).toList();
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(long id) throws NullPointerException {
        return orderRepository.findById(id).orElseThrow(() -> new NullPointerException("Commande n'existe pas."));
    }

    @Override
    @Transactional
    public Order create(Order order, String token) throws PriceException {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        Restaurant restaurant = restaurantService.findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver le restaurant : " + idRestaurant));

        order.setRestaurant(restaurant);
        int articleCount = (order.getArticles() == null ? 0 : order.getArticles().size());
        int menuCount = (order.getMenus() == null ? 0 : order.getMenus().size());
        order.setTotalItems(articleCount + menuCount);
        order.setPayments(new ArrayList<>());

        if (!orderTools.checkPrice(order)) {
            throw new PriceException("Le prix saisie est incorrect");
        }
        order.setNumber(orderTools.setOrderNumber());
        order.setPaymentType(orderTools.setOrderPaymentType(order.getPayments()));
        return orderRepository.save(order);
    }

    @Override
    public Boolean delete(Long id) {
        if (orderRepository.findById(id).isEmpty()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Order addPayment(Order order, Payment payment) {
        Order orderToUpdate = orderRepository.findById(order.getId()).orElseThrow(() -> new RuntimeException("La commande n'existe pas."));
        if (orderToUpdate.getPayments() == null || orderToUpdate.getPayments().isEmpty()) {
            orderToUpdate.setPayments(new ArrayList<>());
        }
        orderToUpdate.getPayments().add(payment);
        orderToUpdate.setPaymentType(orderTools.setOrderPaymentType(orderToUpdate.getPayments()));
        return orderRepository.save(orderToUpdate);
    }

    @Override
    public Order updateStatus(Long id) throws RuntimeException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("La commande n'existe pas."));

        if (order.getStatus().equals(OrderStatus.EN_COURS)) {
            order.setStatus(OrderStatus.TERMINEE);
            return orderRepository.save(order);
        }
        return order;
    }

    @Override
    @Transactional
    public Order patch(Long id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("La commande n'existe pas."));
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Order.class, key);
            if (field != null) {
                field.setAccessible(true);
                if (field.getType().isEnum() && value instanceof String) {
                    Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), (String) value);
                    ReflectionUtils.setField(field, order, enumValue);
                }  else if (field.getType().equals(Date.class) && value instanceof String) {
                    try {
                        Date dateValue = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse((String) value);
                        ReflectionUtils.setField(field, order, dateValue);
                    } catch (ParseException e) {
                        throw new RuntimeException("Invalid date format: " + value);
                    }
                } else {
                    ReflectionUtils.setField(field, order, value);
                }
            }
        });
        return orderRepository.save(order);
    }

    @Override
    public List<OrderListProjection> getAllByDate(Date date) {
        return orderRepository.findAllByDate(new java.sql.Date(date.getTime()));
    }
}
