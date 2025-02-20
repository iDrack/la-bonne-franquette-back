package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.repository.MenuItemRepository;
import org.labonnefranquette.data.services.CacheService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        menuItem1 = new MenuItem();
        menuItem1.setId(1L);
        menuItem1.setOptional(true);
        menuItem1.setExtraPriceHT(50);

        menuItem2 = new MenuItem();
        menuItem2.setId(2L);
        menuItem2.setOptional(false);
        menuItem2.setExtraPriceHT(100);
    }

    @Test
    public void testFindAll() {
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem1, menuItem2));

        List<MenuItem> menuItems = menuItemService.findAll();
        assertEquals(2, menuItems.size());
        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllById() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem1));

        Optional<MenuItem> menuItem = menuItemService.findAllById(1L);
        assertTrue(menuItem.isPresent());
        assertEquals(menuItem1, menuItem.get());
        verify(menuItemRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreate() {
        when(menuItemRepository.save(menuItem1)).thenReturn(menuItem1);

        MenuItem createdMenuItem = menuItemService.create(menuItem1);
        assertEquals(menuItem1, createdMenuItem);
        verify(menuItemRepository, times(1)).save(menuItem1);
        verify(cacheService, times(1));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(menuItemRepository).deleteById(1L);

        menuItemService.deleteById(1L);
        verify(menuItemRepository, times(1)).deleteById(1L);
        verify(cacheService, times(1)).updateCacheVersion();
    }
}
