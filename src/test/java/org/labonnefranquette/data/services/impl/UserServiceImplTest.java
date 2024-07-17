package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    public void createUserSuccessfully() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Boolean result = userService.createUser(userCreateDto);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void createUserWithExistingUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userCreateDto));
    }

    @Test
    public void findByUsernameSuccessfully() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User result = userService.findByUsername("testUser");

        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void findByUsernameWithException() {
        when(userRepository.findByUsername(anyString())).thenThrow(new RuntimeException());

        User result = userService.findByUsername("testUser");

        assertNull(result);
    }

    @Test
    public void getLastConnectionByUsernameSuccessfully() {
        User user = new User();
        user.setLastConnection(new Date());
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        Date result = userService.getLastConnectionByUsername("testUser");

        assertEquals(user.getLastConnection(), result);
    }

    @Test
    public void setLastConnectionByUsernameSuccessfully() {
        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        userService.setLastConnectionByUsername("testUser");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void checkCredentialsSuccessfully() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User result = userService.checkCredentials("testUser", "TestPassword1");

        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void checkCredentialsWithWrongPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        User result = userService.checkCredentials("testUser", "WrongPassword");

        assertNull(result);
    }
}