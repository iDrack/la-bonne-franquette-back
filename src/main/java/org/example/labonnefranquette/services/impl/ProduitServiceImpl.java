package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Produit;
import org.example.labonnefranquette.repository.ProduitRepository;
import org.example.labonnefranquette.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    ProduitRepository produitRepository;

    @Override
    public List<Produit> getAllProduit() {
        return produitRepository.findAll();
    }

    @Override
    public Optional<Produit> getProduitById(long id) {
        return produitRepository.findById(id);
    }
}
