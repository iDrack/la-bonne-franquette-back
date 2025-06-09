package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PaymentTypeTest {

    @Test
    void shouldConstructPaymentTypeAndAccessFields() {
        List<Payment> payments = new ArrayList<>();
        PaymentType paymentType = new PaymentType(1L, "Espèces", true, payments);

        assertEquals(1L, paymentType.getId());
        assertEquals("Espèces", paymentType.getName());
        assertTrue(paymentType.getIsEnable());
        assertSame(payments, paymentType.getPayments());
    }

    @Test
    void shouldSetAndGetFields() {
        PaymentType paymentType = new PaymentType();
        paymentType.setId(2L);
        paymentType.setName("CB");
        paymentType.setIsEnable(false);
        List<Payment> payments = new ArrayList<>();
        paymentType.setPayments(payments);

        assertEquals(2L, paymentType.getId());
        assertEquals("CB", paymentType.getName());
        assertFalse(paymentType.getIsEnable());
        assertSame(payments, paymentType.getPayments());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        ArrayList<Payment> payments1 = new ArrayList<>();
        ArrayList<Payment> payments2 = new ArrayList<>();
        PaymentType pt1 = new PaymentType(1L, "CB", true, payments1);
        PaymentType pt2 = new PaymentType(1L, "CB", true, payments1);
        PaymentType pt3 = new PaymentType(3L, "Chèque", false, payments2);

        assertEquals(pt1, pt2);
        assertEquals(pt1.hashCode(), pt2.hashCode());
        assertNotEquals(pt1, pt3);
        assertNotEquals(pt1.hashCode(), pt3.hashCode());
    }

    @Test
    void shouldHaveToStringWithNameAndId() {
        PaymentType paymentType = new PaymentType(4L, "Ticket", true, new ArrayList<>());
        String str = paymentType.toString();
        assertTrue(str.contains("Ticket"));
        assertTrue(str.contains("4"));
    }

    @Test
    void shouldSerializePaymentTypeToJson() throws JsonProcessingException {
        PaymentType paymentType = new PaymentType(5L, "Carte Restaurant", true, new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(paymentType);

        assertTrue(json.contains("\"id\":5"));
        assertTrue(json.contains("\"name\":\"Carte Restaurant\""));
        assertTrue(json.contains("\"isEnable\":true"));
        assertFalse(json.contains("payments"));
    }

    @Test
    void shouldSetAndGetRestaurant() {
        PaymentType paymentType = new PaymentType();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(400L);
        restaurant.setName("PaymentTypeResto");

        paymentType.setRestaurant(restaurant);

        assertSame(restaurant, paymentType.getRestaurant());
        assertEquals("PaymentTypeResto", paymentType.getRestaurant().getName());
        assertEquals(400L, paymentType.getRestaurant().getId());
    }
}
