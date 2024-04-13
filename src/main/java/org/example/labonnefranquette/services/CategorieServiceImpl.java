package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.Categorie;
import org.example.labonnefranquette.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public List<Categorie> getAllcategorie() {
        return categorieRepository.findAll();
    }
}
