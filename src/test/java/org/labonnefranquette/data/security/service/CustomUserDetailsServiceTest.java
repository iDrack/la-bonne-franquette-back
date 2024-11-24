package org.labonnefranquette.data.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles(Roles.ROLE_USER);
    }

    @Test
    public void loadUserByUsernameSuccessfullyIsNotNull() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");
        // Assert
        assertNotNull(userDetails);
    }

    @Test
    public void loadUserByUsernameSuccessfullyIsGoodUsernameMatches() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");
        // Assert
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameSuccessfullyIsGoodPasswordMatches() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");
        // Assert
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    public void loadUserByUsernameSuccessfullyIsGoodAuthoritiesSize() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");
        // Assert
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    public void loadUserByUsernameSuccessfullyIsGoodAuthorityMatches() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");
        // Assert
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void loadUserByUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername("unknownUser")).thenReturn(null);
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknownUser");
        });
    }
}