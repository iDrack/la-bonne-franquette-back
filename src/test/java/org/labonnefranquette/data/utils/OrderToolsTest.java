package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.Order;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderToolsTest {

    private OrderTools orderTools;

    @BeforeEach
    void setup() {
        orderTools = new OrderTools();
    }

    @Test
    void shouldCycleOrderNumberAbove400() {
        while (orderTools.setOrderNumber() < 399) {
            // Keep setting order numbers until we reach 400
            orderTools.setOrderNumber();
        }
        short nextNumber400 = orderTools.setOrderNumber();
        short nextNumber1 = orderTools.setOrderNumber();
        assertEquals(400, nextNumber400);
        assertEquals(1, nextNumber1);
    }

    @Test
    void shouldCheckCorrectPriceWithArticlesAndMenus() {
        Article art1 = new Article();
        art1.setQuantity(2);
        art1.setTotalPrice(500);

        Article art2 = new Article();
        art2.setQuantity(1);
        art2.setTotalPrice(700);

        Selection menu1 = new Selection();
        menu1.setQuantity(1);
        menu1.setTotalPrice(2000);

        Selection menu2 = new Selection();
        menu2.setQuantity(2);
        menu2.setTotalPrice(1200);

        Order order = new Order();
        order.setArticles(Arrays.asList(art1, art2));
        order.setMenus(Arrays.asList(menu1, menu2));
        order.setTotalPrice(1000 + 700 + 2000 + 2400);

        assertTrue(orderTools.checkPrice(order));
    }

    @Test
    void shouldDetectIncorrectPrice() {
        Article art = new Article();
        art.setQuantity(2);
        art.setTotalPrice(400);

        Order order = new Order();
        order.setArticles(Collections.singletonList(art));
        order.setMenus(Collections.emptyList());
        order.setTotalPrice(1234);

        assertFalse(orderTools.checkPrice(order));
    }

    @Test
    void shouldHandleNullArticlesAndMenus() {
        Order order = new Order();
        order.setArticles(null);
        order.setMenus(null);
        order.setTotalPrice(0);

        assertTrue(orderTools.checkPrice(order));
    }

    @Test
    void shouldIncrementOrderNumber() {
        short number1 = orderTools.setOrderNumber();
        short number2 = orderTools.setOrderNumber();
        assertEquals(number1 + 1, number2);
    }

    @Test
    void shouldReturnAUCUNWhenNoPayment() {
        String type = orderTools.setOrderPaymentType(Collections.emptyList());
        assertEquals("AUCUN", type);
    }

    @Test
    void shouldReturnUniqueTypeWhenOneType() {
        Payment p1 = new Payment();
        p1.setType("CB");
        Payment p2 = new Payment();
        p2.setType("CB");
        List<Payment> payments = Arrays.asList(p1, p2);

        String type = orderTools.setOrderPaymentType(payments);
        assertEquals("CB", type);
    }

    @Test
    void shouldReturnMixedWhenMultipleTypes() {
        Payment p1 = new Payment();
        p1.setType("CB");
        Payment p2 = new Payment();
        p2.setType("ESPECES");
        List<Payment> payments = Arrays.asList(p1, p2);

        String type = orderTools.setOrderPaymentType(payments);
        assertEquals("MIXED", type);
    }
}
