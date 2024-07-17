package org.labonnefranquette.data.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenStatusTest {

    @Test
    public void enumValues_areCorrect() {
        assertEquals(4, TokenStatus.values().length);
        assertEquals(TokenStatus.Valid, TokenStatus.values()[0]);
        assertEquals(TokenStatus.Imminent, TokenStatus.values()[1]);
        assertEquals(TokenStatus.Expired, TokenStatus.values()[2]);
        assertEquals(TokenStatus.Invalid, TokenStatus.values()[3]);
    }
}
