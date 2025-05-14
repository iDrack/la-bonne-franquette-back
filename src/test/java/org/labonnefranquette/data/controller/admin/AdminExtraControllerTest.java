package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AdminExtraControllerTest {

    @Mock
    private GenericService<Addon, Long> extraService;

    @InjectMocks
    private AdminAddonController adminExtraController;
/*TODO

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
    }*/
}