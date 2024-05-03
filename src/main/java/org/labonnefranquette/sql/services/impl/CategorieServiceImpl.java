package org.labonnefranquette.sql.services.impl;

import org.labonnefranquette.sql.model.Categorie;
import org.labonnefranquette.sql.repository.CategorieRepository;
import org.labonnefranquette.sql.services.GenericService;
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
