package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.cache.CacheService;
import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements GenericService<Produit, Long> {

    @Autowired
    ProduitRepository produitRepository;

    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }
    @Override
    public Optional<Produit> findAllById(Long id) {
        return produitRepository.findById(id);
    }
    @Override
    public Produit create(Produit newProduit) {
        CacheService.changeCacheVersion();
        return produitRepository.save(newProduit);
    }
    @Override
    public void deleteById(Long id) {
        CacheService.changeCacheVersion();
        produitRepository.deleteById(id);
    }
}
