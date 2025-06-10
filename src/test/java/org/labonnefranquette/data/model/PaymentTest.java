package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PaymentTest {

    @Test
    void shouldConstructPaymentAndAccessFields() {
        Order order = new Order();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> selections = new ArrayList<>();
        Date date = new Date();

        Payment payment = new Payment(1L, date, "CB", 2300, order, articles, selections);

        assertEquals(1L, payment.getId());
        assertEquals(date, payment.getDate());
        assertEquals("CB", payment.getType());
        assertEquals(2300, payment.getPrice());
        assertSame(order, payment.getOrder());
        assertSame(articles, payment.getArticles());
        assertSame(selections, payment.getSelections());
    }

    @Test
    void shouldSetAndGetFields() {
        Payment payment = new Payment();
        Date date = new Date();
        payment.setId(2L);
        payment.setDate(date);
        payment.setType("Espèces");
        payment.setPrice(1400);
        Order order = new Order();
        payment.setOrder(order);
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> selections = new ArrayList<>();
        payment.setArticles(articles);
        payment.setSelections(selections);

        assertEquals(2L, payment.getId());
        assertEquals(date, payment.getDate());
        assertEquals("Espèces", payment.getType());
        assertEquals(1400, payment.getPrice());
        assertSame(order, payment.getOrder());
        assertSame(articles, payment.getArticles());
        assertSame(selections, payment.getSelections());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Order order = new Order();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> selections = new ArrayList<>();
        Date date = new Date();

        Payment p1 = new Payment(1L, date, "CB", 1000, order, articles, selections);
        Payment p2 = new Payment(2L, date, "CB", 1000, order, articles, selections);
        Payment p3 = new Payment(3L, date, "Espèces", 3000, order, articles, selections);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void shouldHaveToStringWithTypeAndPrice() {
        Payment payment = new Payment(6L, new Date(), "Ticket", 800, new Order(), new ArrayList<>(), new ArrayList<>());
        String str = payment.toString();
        assertTrue(str.contains("Ticket"));
        assertTrue(str.contains("800"));
    }

    @Test
    void shouldSerializePaymentToJson() throws JsonProcessingException {
        Order order = new Order();
        Collection<Article> articles = new ArrayList<>();
        Collection<Selection> selections = new ArrayList<>();
        Date date = new Date();

        Payment payment = new Payment(8L, date, "CB", 500, order, articles, selections);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payment);

        assertTrue(json.contains("\"id\":8"));
        assertTrue(json.contains("\"type\":\"CB\""));
        assertTrue(json.contains("\"price\":500"));
        assertTrue(json.contains("\"articles\":[]"));
        assertTrue(json.contains("\"selections\":[]"));
        assertFalse(json.contains("\"order\""));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        Payment payment = new Payment();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(300L);
        restaurant.setName("Payment Resto");

        payment.setRestaurant(restaurant);

        assertSame(restaurant, payment.getRestaurant());
        assertEquals("Payment Resto", payment.getRestaurant().getName());
        assertEquals(300L, payment.getRestaurant().getId());
    }
}
