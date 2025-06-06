package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenericServiceImplTest {

    @Mock
    private JpaRepository<Addon, Long> repository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private JWTUtil jwtUtil;

    private GenericServiceImpl<Addon, JpaRepository<Addon, Long>, Long> genericService;

    private Restaurant restaurant1;
    private Addon addon1;
    private Addon addon2;
    private String token;

    @BeforeEach
    void setUp() {
        restaurant1 = new Restaurant();
        restaurant1.setId(3L);
        restaurant1.setIsTVAEnable(true);

        addon1 = new Addon();
        addon1.setId(1L);
        addon1.setRestaurant(restaurant1);
        addon1.setVATRate(null);
        addon1.setPrice(100);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(4L);
        restaurant2.setIsTVAEnable(false);
        addon2 = new Addon();
        addon2.setId(2L);
        addon2.setRestaurant(restaurant2);
        addon2.setVATRate(null);
        addon2.setPrice(200);

        token = "dummy-token";

        genericService = new GenericServiceImpl<>(repository);
        ReflectionTestUtils.setField(genericService, "restaurantService", restaurantService);
        ReflectionTestUtils.setField(genericService, "jwtUtil", jwtUtil);
    }

    @Test
    void getAllById_WhenExists_ShouldReturnOptional() {
        when(repository.findById(1L)).thenReturn(Optional.of(addon1));

        Optional<Addon> result = genericService.getAllById(1L);

        assertTrue(result.isPresent());
        assertSame(addon1, result.get());
        verify(repository).findById(1L);
    }

    @Test
    void deleteById_WhenExists_ShouldUpdateVersionAndDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(addon1));

        Optional<Addon> result = genericService.deleteById(1L);

        assertTrue(result.isPresent());
        assertSame(addon1, result.get());
        verify(restaurantService).updateCacheVersion(3L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteById_WhenNotExists_ShouldReturnEmptyAndNoSideEffects() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Addon> result = genericService.deleteById(2L);

        assertFalse(result.isPresent());
        verify(restaurantService, never()).updateCacheVersion(anyLong());
        verify(repository, never()).deleteById(any());
    }

    @Test
    void getAll_ShouldFilterByRestaurantId() {
        when(jwtUtil.extractRestaurantId(token)).thenReturn(3L);
        when(repository.findAll()).thenReturn(Arrays.asList(addon1, addon2));

        List<Addon> result = genericService.getAll(token);

        assertEquals(1, result.size());
        assertSame(addon1, result.get(0));
        verify(jwtUtil).extractRestaurantId(token);
        verify(repository).findAll();
    }

    @Test
    void create_ShouldSetRestaurant_UpdateVersionAndSave() {
        when(jwtUtil.extractRestaurantId(token)).thenReturn(3L);
        when(restaurantService.findAllById(3L)).thenReturn(Optional.of(restaurant1));
        when(repository.save(addon1)).thenReturn(addon1);

        Addon result = genericService.create(addon1, token);

        assertSame(restaurant1, result.getRestaurant());
        verify(restaurantService).updateCacheVersion(3L);
        verify(repository).save(addon1);
    }
}
