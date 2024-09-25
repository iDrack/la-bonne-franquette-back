package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminExtraControllerTest {

    @Mock
    private GenericService<Extra, Long> extraService;

    @InjectMocks
    private AdminExtraController adminExtraController;

    @Test
    public void createNewExtraSuccessfully() {
        Extra extra = new Extra();
        when(extraService.create(extra)).thenReturn(extra);

        ResponseEntity<Extra> response = adminExtraController.createNewExtra(extra);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteExtraSuccessfully() {
        doNothing().when(extraService).deleteById(1L);

        ResponseEntity<?> response = adminExtraController.deleteExtra(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createNewExtraWithNullBody() {
        ResponseEntity<Extra> response = adminExtraController.createNewExtra(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteExtraWithNonExistentId() {
        doNothing().when(extraService).deleteById(999L);

        ResponseEntity<?> response = adminExtraController.deleteExtra(999L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}