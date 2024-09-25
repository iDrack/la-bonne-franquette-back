package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
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
public class AdminCategorieControllerTest {

    @Mock
    private GenericService<Categorie, Long> categorieService;

    @InjectMocks
    private AdminCategorieController adminCategorieController;

    @Test
    public void createNewCategorieSuccessfully() {
        Categorie categorie = new Categorie();
        when(categorieService.create(categorie)).thenReturn(categorie);

        ResponseEntity<Categorie> response = adminCategorieController.createNewCategorie(categorie);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteCategorieSuccessfully() {
        doNothing().when(categorieService).deleteById(1L);

        ResponseEntity<?> response = adminCategorieController.deleteCategorie(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createNewCategorieWithNullBody() {
        ResponseEntity<Categorie> response = adminCategorieController.createNewCategorie(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteCategorieWithNonExistentId() {
        doNothing().when(categorieService).deleteById(999L);

        ResponseEntity<?> response = adminCategorieController.deleteCategorie(999L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}