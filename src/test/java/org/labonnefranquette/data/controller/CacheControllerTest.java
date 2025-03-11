package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CacheControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private CacheController cacheController;

    @Test
    public void getCacheVersionSuccessfully() {
        when(restaurantService.getVersion(0L)).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void getCacheVersionWithAuthToken() {
        when(jwtUtil.extractRestaurantId("valid-token")).thenReturn(1L);
        when(restaurantService.getVersion(1L)).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache("valid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void getCacheVersionWithInvalidAuthToken() {
        when(jwtUtil.extractRestaurantId("invalid-token")).thenReturn(null);
        when(restaurantService.getVersion(null)).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache("invalid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }
}