package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.services.CacheService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CacheControllerTest {

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private CacheController cacheController;

    @Test
    public void getCacheVersionSuccessfully() {
        when(cacheService.getVersion()).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache("");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void getCacheVersionWithAuthToken() {
        when(cacheService.getVersion()).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache("valid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void getCacheVersionWithoutAuthToken() {
        when(cacheService.getVersion()).thenReturn(1);

        ResponseEntity<Integer> response = cacheController.getCache(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void getCacheVersionServiceThrowsException() {
        when(cacheService.getVersion()).thenThrow(new RuntimeException("Service error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cacheController.getCache("");
        });

        assertEquals("Service error", exception.getMessage());
    }

}