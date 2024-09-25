package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/produit")
public class AdminProduitController {

    @Autowired
    GenericService<Produit, Long> produitservice;

    @PostMapping("/create")
    public ResponseEntity<Produit> createNewProduit(@RequestBody Produit produit) {
        if (produit == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Produit>(produitservice.create(produit), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduit(@PathVariable Long id) {
        produitservice.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}