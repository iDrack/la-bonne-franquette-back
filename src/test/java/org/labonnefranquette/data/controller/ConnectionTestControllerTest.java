package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class ConnectionTestControllerTest {

    private final ConnectionTestController connectionTestController = new ConnectionTestController();

    @Test
    public void testConnectionSuccessfully() {
        ResponseEntity<Boolean> response = connectionTestController.testConnection();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }

    @Test
    public void testConnectionResponseNotNull() {
        ResponseEntity<Boolean> response = connectionTestController.testConnection();

        assertNotNull(response);
    }
}