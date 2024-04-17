package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Produit;
import org.example.labonnefranquette.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {

    @Autowired
    ProduitService produitservice;

    @GetMapping("/")
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitservice.getAllProduit(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Optional<Produit> resultat = produitservice.getProduitById(id);
        return resultat.map(produit -> new ResponseEntity<>(produit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
