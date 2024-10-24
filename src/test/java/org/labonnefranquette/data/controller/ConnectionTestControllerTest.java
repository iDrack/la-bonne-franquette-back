package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionTestControllerTest {

    private final ConnectionTestController connectionTestController = new ConnectionTestController();

    @Test
    public void testConnectionSuccessfully() {
        ResponseEntity<Boolean> response = connectionTestController.testConnection();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Connection successful !", response.getBody());
    }

    @Test
    public void testConnectionResponseNotNull() {
        ResponseEntity<Boolean> response = connectionTestController.testConnection();

        assertNotNull(response);
    }
}