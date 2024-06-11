package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {

    @Autowired
    GenericService<Produit, Long> produitservice;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitservice.getAllProduit(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Optional<Produit> resultat = produitservice.findAllById(id);
        return resultat.map(produit -> new ResponseEntity<>(produit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
