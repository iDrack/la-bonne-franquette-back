package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Produit;
import org.example.labonnefranquette.services.ProduitService;
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
    ProduitService produitservice;

    @GetMapping("")
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitservice.getAllProduit(), HttpStatus.OK);
    }
}
