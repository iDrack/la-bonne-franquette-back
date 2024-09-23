package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.services.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class AdminUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminUserController adminUserController;

    @Test
    public void createUserSuccessfully() {
        UserCreateDto userDto = new UserCreateDto();
        ResponseEntity<String> response = adminUserController.createUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Création réussi", response.getBody());
    }

    @Test
    public void createUserWithInvalidData() {
        UserCreateDto userDto = new UserCreateDto();
        doThrow(new IllegalArgumentException()).when(userService).createUser(userDto);

        ResponseEntity<String> response = adminUserController.createUser(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Impossible de créer un utilisateur", response.getBody());
    }

    @Test
    public void createUserWithNullBody() {
        ResponseEntity<String> response = adminUserController.createUser(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Impossible de créer un utilisateur", response.getBody());
    }
}