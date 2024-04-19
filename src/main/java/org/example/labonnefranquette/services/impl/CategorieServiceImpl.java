package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Categorie;
import org.example.labonnefranquette.repository.CategorieRepository;
import org.example.labonnefranquette.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements GenericService<Categorie, Long> {

    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> findAllById(Long id) {
        return categorieRepository.findById(id);
    }
}
