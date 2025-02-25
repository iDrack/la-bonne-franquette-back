package org.labonnefranquette.data.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/categorie")
@Tag(name = "Admin Categories Controller", description = "Controller pour les interractions des administracteurs sur la création des catégories.")
public class AdminCategorieController {
    @Autowired
    GenericService<Categorie, Long> categorieService;
/*
La création et la suppression des catégories n'est pas encore implémenté
    @PostMapping("/create")
    public ResponseEntity<Categorie> createNewCategorie(@RequestBody Categorie categorie,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (categorie == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Categorie>(categorieService.create(categorie), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable Long id,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        categorieService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
*/

}
