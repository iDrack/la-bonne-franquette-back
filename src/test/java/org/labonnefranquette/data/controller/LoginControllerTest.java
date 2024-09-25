package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private AuthServiceImpl authService;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void loginSuccessfully() {
        UserLoginDto userLoginDto = new UserLoginDto();
        when(authService.login(userLoginDto)).thenReturn("valid-token");

        ResponseEntity<String> response = loginController.login(userLoginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("{\"token\":\"valid-token\"}", response.getBody());
    }

    @Test
    public void loginWithInvalidCredentials() {
        UserLoginDto userLoginDto = new UserLoginDto();
        when(authService.login(userLoginDto)).thenReturn(null);

        ResponseEntity<String> response = loginController.login(userLoginDto);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("{\"FORBIDDEN\":\"Vos données ne sont pas correctes\"}", response.getBody());
    }

    @Test
    public void logoutSuccessfully() {
        when(authService.logout("valid-token")).thenReturn(true);

        ResponseEntity<Boolean> response = loginController.logout("valid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
    }

    @Test
    public void logoutWithInvalidToken() {
        when(authService.logout("invalid-token")).thenReturn(false);

        ResponseEntity<Boolean> response = loginController.logout("invalid-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(false, response.getBody());
    }
}