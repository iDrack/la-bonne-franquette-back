package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements GenericService<Ingredient, Long> {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findAllById(Long id) {
        return ingredientRepository.findById(id);
    }
}