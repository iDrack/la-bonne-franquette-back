package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.labonnefranquette.data.services.RestaurantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreateDto userCreateDto;

    @BeforeEach
    public void setup() {
        userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("testUser");
        userCreateDto.setPassword("TestPassword1");
    }

    @Test
    public void createUserWithValidData() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(new Restaurant()));

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("validUser");
        userCreateDto.setPassword("ValidPassword1");
        userCreateDto.setRestaurantId(1L);

        User user = userService.create(userCreateDto);

        assertEquals("validUser", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(Roles.ROLE_USER.toString(), user.getRoles());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void createUserWithInvalidData() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("invalidUser");
        userCreateDto.setPassword("invalid");
        userCreateDto.setRestaurantId(-1L);

        assertThrows(RuntimeException.class, () -> userService.create(userCreateDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void createUserWithExistingUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        when(restaurantService.findAllById(anyLong())).thenReturn(Optional.of(new Restaurant()));

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("existingUser");
        userCreateDto.setPassword("ValidPassword1");
        userCreateDto.setRestaurantId(1L);

        assertThrows(IllegalArgumentException.class, () -> userService.create(userCreateDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void dataIsConformedWithValidData() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("validUser");
        userCreateDto.setPassword("ValidPassword1");

        assertTrue(userService.checkUser(userCreateDto));
    }

    @Test
    public void dataIsConformedWithInvalidData() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("invalidUser");
        userCreateDto.setPassword("invalid");

        assertFalse(userService.checkUser(userCreateDto));
    }

    @Test
    public void isValidUsernameWithNonExistingUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertTrue(userService.checkUsername("newUser"));
    }

    @Test
    public void isValidUsernameWithExistingUser() {
        User user = new User();
        user.setUsername("existingUser");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        assertFalse(userService.checkUsername("existingUser"));
    }

    @Test
    public void isValidPasswordWithValidPassword() {
        assertTrue(userService.checkPassword("ValidPassword1"));
    }

    @Test
    public void isValidPasswordWithInvalidPassword() {
        assertFalse(userService.checkPassword("invalid"));
    }
}