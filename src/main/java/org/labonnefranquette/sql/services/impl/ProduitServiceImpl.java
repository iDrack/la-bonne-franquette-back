package org.labonnefranquette.sql.services.impl;

import org.labonnefranquette.sql.model.Produit;
import org.labonnefranquette.sql.repository.ProduitRepository;
import org.labonnefranquette.sql.services.ProduitService;
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

    @Override
    public Produit createProduit(Produit newProduit) {
        return produitRepository.save(newProduit);
    }


}
