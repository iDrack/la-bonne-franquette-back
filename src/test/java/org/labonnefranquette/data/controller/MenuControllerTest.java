package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MenuControllerTest {

    @Mock
    private GenericServiceImpl<Menu, MenuRepository, Long> menuService;

    @InjectMocks
    private MenuController menuController;

    @Test
    public void getAllMenuSuccessfully() {
        Menu menu = new Menu();
        when(menuService.findAll(anyString())).thenReturn(Arrays.asList(menu));

        ResponseEntity<List<Menu>> response = menuController.getAllMenu("");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
/*TODO

    @Test
    public void getMenuByIdSuccessfully() {
        Menu menu = new Menu();
        when(menuService.findAllById(1L)).thenReturn(Optional.of(menu));

        ResponseEntity<Menu> response = menuController.getMenuById(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getMenuByIdNotFound() {
        when(menuService.findAllById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Menu> response = menuController.getMenuById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }*/
}