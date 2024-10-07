package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategorieControllerTest {

    @Mock
    private GenericService<Categorie, Long> categorieService;

    @InjectMocks
    private CategorieController categorieController;

    @Test
    public void getAllCategorieSuccessfully() {
        Categorie categorie = new Categorie();
        when(categorieService.findAll()).thenReturn(Arrays.asList(categorie));

        ResponseEntity<List<Categorie>> response = categorieController.getAllCategorie();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
/*TODO

    @Test
    public void getCategorieByIdSuccessfully() {
        Categorie categorie = new Categorie();
        when(categorieService.findAllById(1L)).thenReturn(Optional.of(categorie));

        ResponseEntity<Categorie> response = categorieController.getCategorieById(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getCategorieByIdNotFound() {
        when(categorieService.findAllById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Categorie> response = categorieController.getCategorieById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }*/
}