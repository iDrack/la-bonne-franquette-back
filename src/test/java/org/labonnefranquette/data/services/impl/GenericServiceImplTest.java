package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

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
    void deleteById_doesNothingWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        genericService.deleteById(1L);

        verify(restaurantService, never()).updateCacheVersion(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }
}