package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.repository.RestaurantRepository;
import org.labonnefranquette.data.services.impl.RestaurantServiceImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant;
    private User user;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(10L);
        restaurant.setMenuVersion(5);
        restaurant.setName("TestResto");
        restaurant.setEmployees(null);

        user = new User();
        user.setId(20L);
    }

    @Test
    void findAllById_ShouldReturnOptional() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> result = restaurantService.findAllById(10L);

        assertTrue(result.isPresent());
        assertSame(restaurant, result.get());
        verify(restaurantRepository).findById(10L);
    }

    @Test
    void getVersion_WhenExists_ShouldReturnVersion() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        int version = restaurantService.getVersion(10L);

        assertEquals(5, version);
        verify(restaurantRepository).findById(10L);
    }

    @Test
    void getVersion_WhenNotExists_ShouldThrow() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> restaurantService.getVersion(99L));
        assertEquals("Impossible de trouver le restaurant : 99", ex.getMessage());
        verify(restaurantRepository).findById(99L);
    }

    @Test
    void updateCacheVersion_WhenExists_ShouldIncrementAndSave() {
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        restaurantService.updateCacheVersion(10L);

        assertEquals(6, restaurant.getMenuVersion());
        verify(restaurantRepository).findById(10L);
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void updateCacheVersion_WhenNotExists_ShouldThrow() {
        when(restaurantRepository.findById(100L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> restaurantService.updateCacheVersion(100L));
        assertEquals("Impossible de trouver le restaurant : 100", ex.getMessage());
    }

    @Test
    void create_WhenNameExists_ShouldThrow() {
        when(restaurantRepository.existsByName("TestResto")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create("TestResto"));
        assertTrue(ex.getMessage().contains("'TestResto' éxiste déjà"));
        verify(restaurantRepository).existsByName("TestResto");
    }

    @Test
    void create_WhenNameEmpty_ShouldThrow() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create("   "));
        assertTrue(ex1.getMessage().contains("ne peut pas être vide"));

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.create(null));
        assertTrue(ex2.getMessage().contains("ne peut pas être vide"));
    }

    @Test
    void create_WhenValidName_ShouldSaveAndReturnRestaurant() {
        when(restaurantRepository.existsByName("NewResto")).thenReturn(false);

        Restaurant result = restaurantService.create("NewResto");

        assertNotNull(result);
        assertEquals("NewResto", result.getName());
        verify(restaurantRepository).existsByName("NewResto");
        verify(restaurantRepository).save(result);
    }

    @Test
    void delete_ShouldInvokeRepository() {
        restaurantService.delete(restaurant);

        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void addUser_WhenEmployeesNull_ShouldInitializeAndSave() {
        // employees null by default
        Restaurant result = restaurantService.addUser(restaurant, user);

        assertNotNull(result.getEmployees());
        assertEquals(1, result.getEmployees().size());
        assertTrue(result.getEmployees().contains(user));
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void addUser_WhenEmployeesExist_ShouldAddAndSave() {
        List<User> initial = new ArrayList<>();
        initial.add(new User());
        restaurant.setEmployees(initial);

        Restaurant result = restaurantService.addUser(restaurant, user);

        assertEquals(2, result.getEmployees().size());
        assertTrue(result.getEmployees().contains(user));
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void getAllUser_WhenExists_ShouldReturnEmployees() {
        List<User> employees = List.of(user);
        Restaurant r2 = new Restaurant();
        r2.setEmployees(employees);
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(r2));

        List<User> result = restaurantService.getAllUser(10L);

        assertSame(employees, result);
        verify(restaurantRepository).findById(10L);
    }

    @Test
    void getAllUser_WhenNotExists_ShouldThrow() {
        when(restaurantRepository.findById(11L)).thenReturn(Optional.empty());

        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> restaurantService.getAllUser(11L));
        assertTrue(ex.getMessage().contains("Impossible de trouver le restaurant avec l'id : 11"));
    }
}
