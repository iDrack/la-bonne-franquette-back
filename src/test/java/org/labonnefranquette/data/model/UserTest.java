package org.labonnefranquette.data.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.Roles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void setUsername_setsUsernameCorrectly() {
        user.setUsername("testUser");
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void setPassword_setsPasswordCorrectly() {
        user.setPassword("testPassword");
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    public void setRoles_setsSingleRoleCorrectly() {
        user.setRoles(Roles.ROLE_ADMIN);
        assertEquals("ROLE_ADMIN", user.getRoles());
    }

    @Test
    public void setRoles_appendsMultipleRolesCorrectly() {
        user.setRoles(Roles.ROLE_ADMIN);
        user.setRoles(Roles.ROLE_USER);
        assertEquals("ROLE_ADMIN, ROLE_USER", user.getRoles());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        user.setUsername("testUser");
        assertEquals("user=testUser.", user.toString());
    }
}