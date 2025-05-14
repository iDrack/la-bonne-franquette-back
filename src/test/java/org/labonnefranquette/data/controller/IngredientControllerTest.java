package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class IngredientControllerTest {

    @Mock
    IngredientRepository repo;

    @Mock
    private GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @Test
    public void getAllIngredientsSuccessfully() {
        Ingredient ingredient = new Ingredient();
        when(ingredientService.getAll(anyString())).thenReturn(Arrays.asList(ingredient));

        ResponseEntity<List<Ingredient>> response = ingredientController.getAllIngredients("");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
/*TODO

    @Test
    public void getIngredientByIdSuccessfully() {
        Ingredient ingredient = new Ingredient();
        when(ingredientService.findAllById(1L)).thenReturn(Optional.of(ingredient));

        ResponseEntity<Ingredient> response = ingredientController.getIngredientById(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getIngredientByIdNotFound() {
        when(ingredientService.findAllById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Ingredient> response = ingredientController.getIngredientById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }*/
}