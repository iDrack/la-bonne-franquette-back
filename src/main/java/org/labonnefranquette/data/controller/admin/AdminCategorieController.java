package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categorie")
public class AdminCategorieController {

    @Autowired
    GenericService<Categorie, Long> categorieService;

    @PostMapping("/create")
    public ResponseEntity<Categorie> createNewCategorie(@RequestBody Categorie categorie) {
        if (categorie == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Categorie>(categorieService.create(categorie), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
