package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.services.GenericService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProduitControllerTest {

    @Mock
    private GenericService<Produit, Long> produitService;

    @InjectMocks
    private ProduitController produitController;

    @Test
    public void getAllProduitsSuccessfully() {
        Produit produit = new Produit();
        when(produitService.findAll()).thenReturn(Arrays.asList(produit));

        ResponseEntity<List<Produit>> response = produitController.getAllProduits();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

/*TODO

    @Test
    public void getProduitByIdSuccessfully() {
        Produit produit = new Produit();
        when(produitService.findAllById(1L)).thenReturn(Optional.of(produit));

        ResponseEntity<Produit> response = produitController.getProduitById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getProduitByIdNotFound() {
        when(produitService.findAllById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Produit> response = produitController.getProduitById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }*/
}