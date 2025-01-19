package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenericServiceImplTest {

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private GenericServiceImpl<Categorie, CategorieRepository, Long> categorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnAllCategories() {
        List<Categorie> categories = Collections.singletonList(new Categorie());
        when(categorieRepository.findAll()).thenReturn(categories);

        List<Categorie> result = categorieService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAllById_shouldReturnCategoryWhenIdExists() {
        Categorie categorie = new Categorie();
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));

        Optional<Categorie> result = categorieService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(categorie, result.get());
    }

    @Test
    void findAllById_shouldReturnEmptyWhenIdDoesNotExist() {
        when(categorieRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Categorie> result = categorieService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void create_shouldSaveAndReturnNewCategory() {
        Categorie newCategorie = new Categorie();
        when(categorieRepository.save(newCategorie)).thenReturn(newCategorie);

        Categorie result = categorieService.create(newCategorie);

        assertNotNull(result);
        assertEquals(newCategorie, result);
        verify(categorieRepository, times(1)).save(newCategorie);
    }

    @Test
    void deleteById_shouldDeleteCategory() {
        doNothing().when(categorieRepository).deleteById(1L);

        categorieService.deleteById(1L);

        verify(categorieRepository, times(1)).deleteById(1L);
    }
}
