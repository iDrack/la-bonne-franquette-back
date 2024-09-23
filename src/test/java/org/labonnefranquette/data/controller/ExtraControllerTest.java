package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExtraControllerTest {

    @Mock
    private GenericService<Extra, Long> extraService;

    @InjectMocks
    private ExtraController extraController;

    @Test
    public void getAllExtraSuccessfully() {
        Extra extra = new Extra();
        when(extraService.findAll()).thenReturn(Arrays.asList(extra));

        ResponseEntity<List<Extra>> response = extraController.getAllExtra();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getExtraByIdSuccessfully() {
        Extra extra = new Extra();
        when(extraService.findAllById(1L)).thenReturn(Optional.of(extra));

        ResponseEntity<Extra> response = extraController.getExtraById(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getExtraByIdNotFound() {
        when(extraService.findAllById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Extra> response = extraController.getExtraById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}