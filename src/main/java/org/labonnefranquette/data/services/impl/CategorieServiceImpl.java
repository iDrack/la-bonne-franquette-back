package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
import org.labonnefranquette.data.cache.CacheService;
import org.labonnefranquette.data.services.GenericService;
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
    @Override
    public Categorie create(Categorie newCategorie) {
        CacheService.changeCacheVersion();
        return categorieRepository.save(newCategorie);
    }
    @Override
    public void deleteById(Long id) {
        CacheService.changeCacheVersion();
        categorieRepository.deleteById(id);
    }
}
