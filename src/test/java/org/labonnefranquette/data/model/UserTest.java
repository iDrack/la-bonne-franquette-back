package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.enums.Roles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class UserTest {

    @Test
    void shouldSetAndGetFields() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john.doe");
        user.setPassword("superSecret");
        Date now = new Date();
        user.setLastConnection(now);
        user.setRoles(Roles.ROLE_ADMIN);
        Restaurant restaurant = new Restaurant();
        user.setRestaurant(restaurant);

        assertEquals(1L, user.getId());
        assertEquals("john.doe", user.getUsername());
        assertEquals("superSecret", user.getPassword());
        assertEquals(now, user.getLastConnection());
        assertEquals("ROLE_ADMIN", user.getRoles());
        assertSame(restaurant, user.getRestaurant());
    }

    @Test
    void shouldSetAndAddRolesProperly() {
        User user = new User();
        user.setRoles(Roles.ROLE_USER);
        assertEquals("ROLE_USER", user.getRoles());

        user.setRoles(Roles.ROLE_ADMIN);
        assertEquals("ROLE_USER, ROLE_ADMIN", user.getRoles());

        user.setRoles(Roles.ROLE_MANAGER);
        assertEquals("ROLE_USER, ROLE_ADMIN, ROLE_MANAGER", user.getRoles());
    }

    @Test
    void shouldResetRoles() {
        User user = new User();
        user.setRoles(Roles.ROLE_USER);
        assertFalse(user.getRoles().isEmpty());
        user.resetRoles();
        assertEquals("", user.getRoles());
    }

    @Test
    void shouldHaveToStringWithUsername() {
        User user = new User();
        user.setUsername("mario");
        String str = user.toString();
        assertTrue(str.contains("user=mario"));
    }

    @Test
    void shouldSerializeUserToJson() throws JsonProcessingException {
        Restaurant restaurant = new Restaurant();
        User user = new User();
        user.setId(8L);
        user.setUsername("jane");
        user.setPassword("pwd");
        user.setRoles(Roles.ROLE_USER);
        user.setRestaurant(restaurant);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        assertTrue(json.contains("\"id\":8"));
        assertTrue(json.contains("\"username\":\"jane\""));
        assertTrue(json.contains("\"password\":\"pwd\""));
        assertTrue(json.contains("\"roles\":\"ROLE_USER\""));
        assertTrue(json.contains("restaurant"));
    }
}
