package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
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
public class CategorieControllerTest {

    @Mock
    private GenericServiceImpl<Categorie, CategorieRepository, Long> categorieService;

    @InjectMocks
    private CategorieController categorieController;

    @Test
    public void getAllCategorieSuccessfully() {
        Categorie categorie = new Categorie();
        when(categorieService.findAll(anyString())).thenReturn(Arrays.asList(categorie));

        ResponseEntity<List<Categorie>> response = categorieController.getAllCategorie("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getAllCategorieWithEmptyList() {
        when(categorieService.findAll(anyString())).thenReturn(Arrays.asList());

        ResponseEntity<List<Categorie>> response = categorieController.getAllCategorie("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void getAllCategorieWithNullAuthToken() {
        Categorie categorie = new Categorie();
        when(categorieService.findAll(null)).thenReturn(Arrays.asList(categorie));

        ResponseEntity<List<Categorie>> response = categorieController.getAllCategorie(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}