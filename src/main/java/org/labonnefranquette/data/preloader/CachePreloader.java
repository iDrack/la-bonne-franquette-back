package org.labonnefranquette.data.preloader;

import org.labonnefranquette.data.repository.CategorieRepository;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.labonnefranquette.data.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CachePreloader implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CacheService cacheService;

    @Override
    public void run(String... args) throws Exception {
        categorieRepository.findAll();
        produitRepository.findAll();
        ingredientRepository.findAll();
        menuRepository.findAll();
        cacheService.incrementCacheVersion();
    }
}