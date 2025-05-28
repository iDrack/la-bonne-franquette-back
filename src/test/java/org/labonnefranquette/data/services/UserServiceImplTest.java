package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserUpdateDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.labonnefranquette.data.services.impl.UserServiceImpl;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private DtoTools dtoTools;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreateDto createDto;
    private UserUpdateDto updateDto;
    private Restaurant restaurant;
    private User existingUser;

    @BeforeEach
    void setUp() {
        createDto = new UserCreateDto();
        updateDto = new UserUpdateDto();
        restaurant = new Restaurant();
        restaurant.setId(1L);
        existingUser = new User();
        existingUser.setId(2L);
        existingUser.setUsername("oldUser");
        existingUser.setPassword("encodedOld");
        existingUser.setRoles(Roles.ROLE_MANAGER);
        existingUser.setRestaurant(restaurant);
    }

    @Test
    void create_WhenRestaurantNotFound_ShouldThrowRuntimeException() {
        createDto.setRestaurantId(99L);
        when(restaurantService.findAllById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.create(createDto));
        assertTrue(ex.getMessage().contains("restaurant introuvable"));
    }

    @Test
    void create_WhenCheckUserFails_ShouldThrowIllegalArgumentException() {
        createDto.setRestaurantId(1L);
        createDto.setUsername(null); // invalid username
        when(restaurantService.findAllById(1L)).thenReturn(Optional.of(restaurant));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.create(createDto));
        assertTrue(ex.getMessage().contains("Informations de connexions incorrectes"));
    }

    @Test
    void create_WhenUsernameExists_ShouldThrowIllegalArgumentException() {
        createDto.setRestaurantId(1L);
        createDto.setUsername("dup");
        createDto.setPassword("Valid1Pass");
        when(restaurantService.findAllById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.existsByUsername("dup")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.create(createDto));
    }

    @Test
    void create_WhenValid_ShouldSaveAndReturnUser_DefaultRole() {
        createDto.setRestaurantId(1L);
        createDto.setUsername("userX");
        createDto.setPassword("Passw0rd");
        createDto.setRole(null);
        when(restaurantService.findAllById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.existsByUsername("userX")).thenReturn(false);
        when(passwordEncoder.encode("Passw0rd")).thenReturn("encodedPwd");

        User result = userService.create(createDto);

        assertNotNull(result);
        assertEquals("userX", result.getUsername());
        assertEquals("encodedPwd", result.getPassword());
        assertEquals(Roles.ROLE_USER.name(), result.getRoles());
        assertSame(restaurant, result.getRestaurant());
        verify(userRepository).save(result);
    }

    @Test
    void create_WhenValid_WithAdminRole_ShouldSaveAndReturnUser_AdminRole() {
        createDto.setRestaurantId(1L);
        createDto.setUsername("adminU");
        createDto.setPassword("Passw0rd");
        createDto.setRole("ROLE_ADMIN");
        when(restaurantService.findAllById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.existsByUsername("adminU")).thenReturn(false);
        when(passwordEncoder.encode("Passw0rd")).thenReturn("encodedPwd2");

        User result = userService.create(createDto);

        assertEquals(Roles.ROLE_ADMIN.name(), result.getRoles());
        verify(userRepository).save(result);
    }

    @Test
    void getByUsername_ShouldDelegateToRepository() {
        when(userRepository.findByUsername("u1")).thenReturn(existingUser);
        User result = userService.getByUsername("u1");
        assertSame(existingUser, result);
    }

    @Test
    void setAdmin_ShouldChangeRoleToAdminAndSave() {
        User u = new User();
        u.setRoles(Roles.ROLE_USER);
        User result = userService.setAdmin(u);
        assertEquals(Roles.ROLE_ADMIN.name(), result.getRoles());
        verify(userRepository).save(u);
    }

    @Test
    void deleteByUsername_WhenUserNull_ShouldReturnFalse() {
        when(userRepository.findByUsername("nope")).thenReturn(null);
        assertFalse(userService.deleteByUsername("nope"));
    }

    @Test
    void deleteByUsername_WhenUserExists_ShouldDeleteAndReturnTrue() {
        when(userRepository.findByUsername("oldUser")).thenReturn(existingUser);
        boolean result = userService.deleteByUsername("oldUser");
        assertTrue(result);
        verify(userRepository).deleteById(2L);
    }

    @Test
    void update_WhenCheckUserFails_ShouldThrowIllegalArgumentException() {
        updateDto.setOldUsername(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.update(updateDto));
        assertTrue(ex.getMessage().contains("Informations de connexions incorrectes"));
    }

    @Test
    void update_WhenUsernameChangedAndExists_ShouldThrowIllegalArgumentException() {
        updateDto.setOldUsername("oldUser");
        updateDto.setUsername("newUser");
        updateDto.setPassword("Passw0rd");
        when(userRepository.existsByUsername("newUser")).thenReturn(true);
        // pass checkUser
        assertThrows(IllegalArgumentException.class,
                () -> userService.update(updateDto));
    }

    @Test
    void update_WhenUserNotFound_ShouldReturnNull() {
        updateDto.setOldUsername("nope");
        updateDto.setUsername("new");
        when(userRepository.findByUsername("nope")).thenReturn(null);
        updateDto.setPassword("Passw0rd");
        User result = userService.update(updateDto);
        assertNull(result);
    }

    @Test
    void update_WhenOldPasswordMismatch_ShouldThrowIllegalArgumentException() {
        updateDto.setOldUsername("oldUser");
        updateDto.setUsername("oldUser");
        updateDto.setOldPassword("wrongOld");
        updateDto.setPassword("Passw0rd");
        when(userRepository.findByUsername("oldUser")).thenReturn(existingUser);
        when(passwordEncoder.matches("wrongOld", "encodedOld")).thenReturn(false);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.update(updateDto));
        assertTrue(ex.getMessage().contains("ancien mot de passe est incorrect"));
    }

    @Test
    void update_WhenValid_ShouldUpdateFieldsAndSave() {
        updateDto.setOldUsername("oldUser");
        updateDto.setUsername("newU");
        updateDto.setOldPassword("oldP");
        updateDto.setPassword("NewP@ss1");
        updateDto.setRole("ROLE_MANAGER");
        when(userRepository.findByUsername("oldUser")).thenReturn(existingUser);
        when(passwordEncoder.matches("oldP", "encodedOld")).thenReturn(true);
        when(passwordEncoder.encode("NewP@ss1")).thenReturn("encNew");

        User result = userService.update(updateDto);

        assertEquals("newU", result.getUsername());
        assertEquals("encNew", result.getPassword());
        assertEquals(Roles.ROLE_MANAGER.name(), result.getRoles());
        verify(userRepository).save(existingUser);
    }
}
