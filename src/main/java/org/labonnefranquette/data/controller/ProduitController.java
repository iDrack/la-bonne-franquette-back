package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
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
    private GenericServiceImpl<Produit, ProduitRepository, Long> produitService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitService.findAll(), HttpStatus.OK);
    }
}