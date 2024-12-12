package org.labonnefranquette.data.model.enums;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class RolesTest {

    @Test
    public void enumValues_areCorrect() {
        assertEquals(3, Roles.values().length);
        assertEquals(Roles.ROLE_USER, Roles.values()[0]);
        assertEquals(Roles.ROLE_MANAGER, Roles.values()[1]);
        assertEquals(Roles.ROLE_ADMIN, Roles.values()[2]);
    }
}
