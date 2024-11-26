package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu menu;

    @BeforeEach
    public void setup() {
        menu = new Menu();
        menu.setId(1L);
        menu.setNom("Test");
    }

    @Test
    public void findAllMenusSuccessfully() {
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));

        List<Menu> result = menuService.findAll();

        assertEquals(1, result.size());
        assertEquals(menu, result.get(0));
    }

    @Test
    public void findMenuByIdSuccessfully() {
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));

        Optional<Menu> result = menuService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(menu, result.get());
    }

    @Test
    public void findMenuByIdNotFound() {
        when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Menu> result = menuService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void createMenuSuccessfully() {
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        Menu result = menuService.create(menu);

        assertEquals(menu, result);
    }

    @Test
    public void deleteMenuByIdSuccessfully() {
        doNothing().when(menuRepository).deleteById(anyLong());

        menuService.deleteById(1L);

        verify(menuRepository, times(1)).deleteById(anyLong());
    }
}