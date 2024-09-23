package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Menu;
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
public class AdminMenuControllerTest {

    @Mock
    private GenericService<Menu, Long> menuService;

    @InjectMocks
    private AdminMenuController adminMenuController;

    @Test
    public void createNewMenuSuccessfully() {
        Menu menu = new Menu();
        when(menuService.create(menu)).thenReturn(menu);

        ResponseEntity<Menu> response = adminMenuController.createNewMenu(menu);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteMenuSuccessfully() {
        doNothing().when(menuService).deleteById(1L);

        ResponseEntity<?> response = adminMenuController.deleteMenu(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createNewMenuWithNullBody() {
        ResponseEntity<Menu> response = adminMenuController.createNewMenu(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteMenuWithNonExistentId() {
        doNothing().when(menuService).deleteById(999L);

        ResponseEntity<?> response = adminMenuController.deleteMenu(999L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}