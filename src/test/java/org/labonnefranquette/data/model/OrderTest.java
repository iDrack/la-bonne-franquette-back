package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.OrderStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class OrderTest {

    @Test
    void shouldConstructOrderAndAccessFields() {
        Date creationDate = new Date();
        Date deliveryDate = new Date();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> menus = new ArrayList<>();
        Collection<Payment> payments = new ArrayList<>();

        Order order = new Order(
                1L,
                (short) 12,
                creationDate,
                deliveryDate,
                OrderStatus.EN_COURS,
                true,
                3,
                articles,
                menus,
                payments,
                "CB",
                4500,
                true
        );

        assertEquals(1L, order.getId());
        assertEquals(12, order.getNumber());
        assertEquals(creationDate, order.getCreationDate());
        assertEquals(deliveryDate, order.getDeliveryDate());
        assertEquals(OrderStatus.EN_COURS, order.getStatus());
        assertTrue(order.getDineIn());
        assertEquals(3, order.getTotalItems());
        assertSame(articles, order.getArticles());
        assertSame(menus, order.getMenus());
        assertSame(payments, order.getPayments());
        assertEquals("CB", order.getPaymentType());
        assertEquals(4500, order.getTotalPrice());
        assertTrue(order.isPaid());
    }

    @Test
    void shouldSetAndGetFields() {
        Order order = new Order();
        Date creationDate = new Date();
        Date deliveryDate = new Date();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> menus = new ArrayList<>();
        Collection<Payment> payments = new ArrayList<>();

        order.setId(2L);
        order.setNumber((short) 34);
        order.setCreationDate(creationDate);
        order.setDeliveryDate(deliveryDate);
        order.setStatus(OrderStatus.TERMINEE);
        order.setDineIn(false);
        order.setTotalItems(5);
        order.setArticles(articles);
        order.setMenus(menus);
        order.setPayments(payments);
        order.setPaymentType("Espèces");
        order.setTotalPrice(9800);
        order.setPaid(false);

        assertEquals(2L, order.getId());
        assertEquals(34, order.getNumber());
        assertEquals(creationDate, order.getCreationDate());
        assertEquals(deliveryDate, order.getDeliveryDate());
        assertEquals(OrderStatus.TERMINEE, order.getStatus());
        assertFalse(order.getDineIn());
        assertEquals(5, order.getTotalItems());
        assertSame(articles, order.getArticles());
        assertSame(menus, order.getMenus());
        assertSame(payments, order.getPayments());
        assertEquals("Espèces", order.getPaymentType());
        assertEquals(9800, order.getTotalPrice());
        assertFalse(order.isPaid());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Date creationDate = new Date();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> menus = new ArrayList<>();
        Collection<Payment> payments = new ArrayList<>();

        Order o1 = new Order(1L, (short)1, creationDate, null, OrderStatus.EN_COURS, true, 1, articles, menus, payments, "CB", 2000, false);
        Order o2 = new Order(1L, (short)1, creationDate, null, OrderStatus.EN_COURS, true, 1, articles, menus, payments, "CB", 2000, false);
        Order o3 = new Order(3L, (short)2, creationDate, null, OrderStatus.TERMINEE, false, 5, articles, menus, payments, "Espèces", 8000, true);

        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
        assertNotEquals(o1, o3);
        assertNotEquals(o1.hashCode(), o3.hashCode());
    }

    @Test
    void shouldHaveToStringWithIdAndStatus() {
        Order order = new Order(4L, (short)99, new Date(), null, OrderStatus.ANNULEE, false, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "CB", 500, false);
        String str = order.toString();
        assertTrue(str.contains("id=4"));
        assertTrue(str.contains("number=99"));
        assertTrue(str.contains("ANNULEE"));
    }

    @Test
    void shouldSerializeOrderToJson() throws JsonProcessingException {
        Date creationDate = new Date();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> menus = new ArrayList<>();
        Collection<Payment> payments = new ArrayList<>();

        Order order = new Order(5L, (short)11, creationDate, null, OrderStatus.TERMINEE, true, 6, articles, menus, payments, "CB", 2400, true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"number\":11"));
        assertTrue(json.contains("\"status\":\"TERMINEE\""));
        assertTrue(json.contains("\"dineIn\":true"));
        assertTrue(json.contains("\"totalItems\":6"));
        assertTrue(json.contains("\"totalPrice\":2400"));
        assertTrue(json.contains("\"paid\":true"));
        assertTrue(json.contains("\"paymentType\":\"CB\""));
        assertTrue(json.contains("\"articles\":[]"));
        assertTrue(json.contains("\"menus\":[]"));
        assertTrue(json.contains("\"payments\":[]"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(222L);
        restaurant.setName("OrderTestResto");
        order.setRestaurant(restaurant);

        assertSame(restaurant, order.getRestaurant());
        assertEquals("OrderTestResto", order.getRestaurant().getName());
        assertEquals(222L, order.getRestaurant().getId());
    }

    @Test
    void shouldSetAndGetTotalPrice() {
        Order order = new Order();
        order.setTotalPrice(1500);
        assertEquals(1500, order.getTotalPrice());
    }
}
