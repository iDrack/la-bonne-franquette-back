package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminProduitControllerTest {

    @Mock
    private GenericService<Produit, Long> produitService;

    @InjectMocks
    private AdminProduitController adminProduitController;
/*TODO

    @Test
    public void createNewProduitSuccessfully() {
        Produit produit = new Produit();
        when(produitService.create(produit)).thenReturn(produit);

        ResponseEntity<Produit> response = adminProduitController.createNewProduit(produit);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteProduitSuccessfully() {
        doNothing().when(produitService).deleteById(1L);

        ResponseEntity<?> response = adminProduitController.deleteProduit(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createNewProduitWithNullBody() {
        ResponseEntity<Produit> response = adminProduitController.createNewProduit(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteProduitWithNonExistentId() {
        doNothing().when(produitService).deleteById(999L);

        ResponseEntity<?> response = adminProduitController.deleteProduit(999L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }*/
}