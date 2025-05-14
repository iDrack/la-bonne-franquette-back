package org.labonnefranquette.data.controller.admin;

import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.services.GenericService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AdminCategorieControllerTest {

    @Mock
    private GenericService<Category, Long> categorieService;

    @InjectMocks
    private AdminCategoryController adminCategorieController;
/*TODO

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
    }*/
}