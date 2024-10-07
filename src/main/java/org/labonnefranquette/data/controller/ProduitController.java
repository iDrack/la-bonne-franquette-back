package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {

    @Autowired
    GenericService<Produit, Long> produitservice;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitservice.findAll(), HttpStatus.OK);
    }

/*
Les produits ne sont jamais récupéré par leur id
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Optional<Produit> resultat = produitservice.findAllById(id);
        return resultat.map(produit -> new ResponseEntity<>(produit, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
*/
}
