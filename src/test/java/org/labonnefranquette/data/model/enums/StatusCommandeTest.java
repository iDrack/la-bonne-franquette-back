package org.labonnefranquette.data.model.enums;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class StatusCommandeTest {

    @Test
    public void enumValues_areCorrect() {
        assertEquals(4, OrderStatus.values().length);
        assertEquals(OrderStatus.EN_COURS, OrderStatus.values()[0]);
        assertEquals(OrderStatus.TERMINEE, OrderStatus.values()[1]);
        assertEquals(OrderStatus.LIVREE, OrderStatus.values()[2]);
        assertEquals(OrderStatus.ANNULEE, OrderStatus.values()[3]);
    }
}
