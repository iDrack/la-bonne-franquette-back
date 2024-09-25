package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.services.impl.IngredientServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminIngredientControllerTest {

    @Mock
    private IngredientServiceImpl ingredientService;

    @InjectMocks
    private AdminIngredientController adminIngredientController;

    @Test
    public void createNewIngredientSuccessfully() {
        Ingredient ingredient = new Ingredient();
        when(ingredientService.create(ingredient)).thenReturn(ingredient);

        ResponseEntity<Ingredient> response = adminIngredientController.createNewIngredient(ingredient);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteIngredientSuccessfully() {
        doNothing().when(ingredientService).deleteById(1L);

        ResponseEntity<?> response = adminIngredientController.deleteIngredient(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createNewIngredientWithNullBody() {
        ResponseEntity<Ingredient> response = adminIngredientController.createNewIngredient(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteIngredientWithNonExistentId() {
        doNothing().when(ingredientService).deleteById(999L);

        ResponseEntity<?> response = adminIngredientController.deleteIngredient(999L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}