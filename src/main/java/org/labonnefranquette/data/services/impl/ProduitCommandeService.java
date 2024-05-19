package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.ProduitCommande;
import org.labonnefranquette.data.repository.ProduitCommandeRepository;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitCommandeService implements GenericService<ProduitCommande, Long> {

    @Autowired
    private ProduitCommandeRepository produitCommandeRepository;

    @Override
    public List<ProduitCommande> findAll() {
        return produitCommandeRepository.findAll();
    }

    @Override
    public Optional<ProduitCommande> findAllById(Long id) {
        return produitCommandeRepository.findById(id);
    }
}
