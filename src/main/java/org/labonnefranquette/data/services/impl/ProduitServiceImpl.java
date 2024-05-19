package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.labonnefranquette.data.services.ProduitService;
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
