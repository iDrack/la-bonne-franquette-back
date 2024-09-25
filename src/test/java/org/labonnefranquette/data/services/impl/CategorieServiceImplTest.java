package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategorieServiceImplTest {

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private CategorieServiceImpl categorieService;

    private Categorie categorie;

    @BeforeEach
    public void setup() {
        categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Test");
    }

    @Test
    public void findAllCategoriesSuccessfully() {
        when(categorieRepository.findAll()).thenReturn(Arrays.asList(categorie));

        List<Categorie> result = categorieService.findAll();

        assertEquals(1, result.size());
        assertEquals(categorie, result.get(0));
    }

    @Test
    public void findCategorieByIdSuccessfully() {
        when(categorieRepository.findById(anyLong())).thenReturn(Optional.of(categorie));

        Optional<Categorie> result = categorieService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(categorie, result.get());
    }

    @Test
    public void findCategorieByIdNotFound() {
        when(categorieRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Categorie> result = categorieService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void createCategorieSuccessfully() {
        when(categorieRepository.save(any(Categorie.class))).thenReturn(categorie);

        Categorie result = categorieService.create(categorie);

        assertEquals(categorie, result);
    }

    @Test
    public void deleteCategorieByIdSuccessfully() {
        doNothing().when(categorieRepository).deleteById(anyLong());

        categorieService.deleteById(1L);

        verify(categorieRepository, times(1)).deleteById(anyLong());
    }
}