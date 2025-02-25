package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenericServiceImplTest {


    @Mock
    private RestaurantService restaurantService;

    @Mock
    private JpaRepository<HasRestaurant, Long> repository;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private GenericServiceImpl<HasRestaurant, JpaRepository<HasRestaurant, Long>, Long> genericService;

    @BeforeEach
    void setUp() {
        genericService = new GenericServiceImpl<>(repository);
    }

    @Test
    void findAll_returnsAllItems() {
        List<HasRestaurant> items = List.of(mock(HasRestaurant.class), mock(HasRestaurant.class));
        when(repository.findAll()).thenReturn(items);

        List<HasRestaurant> result = genericService.findAll();

        assertEquals(items, result);
    }

    @Test
    void findAllById_returnsItemWhenFound() {
        HasRestaurant item = mock(HasRestaurant.class);
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        Optional<HasRestaurant> result = genericService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(item, result.get());
    }

    @Test
    void findAllById_returnsEmptyWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<HasRestaurant> result = genericService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteById_deletesItemAndUpdatesCacheWhenFound() {
        HasRestaurant item = mock(HasRestaurant.class);
        when(item.getRestaurant()).thenReturn((Restaurant) mock(HasRestaurant.class));
        when(item.getRestaurant().getId()).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        genericService.deleteById(1L);

        verify(restaurantService).updateCacheVersion(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteById_doesNothingWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        genericService.deleteById(1L);

        verify(restaurantService, never()).updateCacheVersion(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void findAll_withToken_returnsFilteredItems() {
        HasRestaurant item1 = mock(HasRestaurant.class);
        HasRestaurant item2 = mock(HasRestaurant.class);
        when(item1.getRestaurant().getId()).thenReturn(1L);
        when(item2.getRestaurant().getId()).thenReturn(2L);
        when(repository.findAll()).thenReturn(List.of(item1, item2));
        when(jwtUtil.extractRestaurantId("token")).thenReturn(1L);

        List<HasRestaurant> result = genericService.findAll("token");

        assertEquals(1, result.size());
        assertEquals(item1, result.get(0));
    }

    @Test
    void create_savesNewItemAndUpdatesCache() {
        HasRestaurant newItem = mock(HasRestaurant.class);
        Restaurant restaurant = mock(Restaurant.class);
        when(jwtUtil.extractRestaurantId("token")).thenReturn(1L);
        when(restaurantService.findAllById(1L)).thenReturn(Optional.of(restaurant));
        when(repository.save(newItem)).thenReturn(newItem);

        HasRestaurant result = genericService.create(newItem, "token");

        verify(newItem).setRestaurant(any(Restaurant.class));
        verify(restaurantService).updateCacheVersion(1L);
        assertEquals(newItem, result);
    }

    @Test
    void create_throwsExceptionWhenRestaurantNotFound() {
        HasRestaurant newItem = mock(HasRestaurant.class);
        when(jwtUtil.extractRestaurantId("token")).thenReturn(1L);
        when(restaurantService.findAllById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> genericService.create(newItem, "token"));

        assertEquals("Impossible de trouver de restaurant : 1", exception.getMessage());
    }
}