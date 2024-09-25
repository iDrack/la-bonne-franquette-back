package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    public void setup() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setNom("Test");
    }

    @Test
    public void findAllIngredientsSuccessfully() {
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));

        List<Ingredient> result = ingredientService.findAll();

        assertEquals(1, result.size());
        assertEquals(ingredient, result.get(0));
    }

    @Test
    public void findIngredientByIdSuccessfully() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> result = ingredientService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(ingredient, result.get());
    }

    @Test
    public void findIngredientByIdNotFound() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Ingredient> result = ingredientService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void createIngredientSuccessfully() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient result = ingredientService.create(ingredient);

        assertEquals(ingredient, result);
    }

    @Test
    public void deleteIngredientByIdSuccessfully() {
        doNothing().when(ingredientRepository).deleteById(anyLong());

        ingredientService.deleteById(1L);

        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }
}