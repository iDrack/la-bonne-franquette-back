package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Ingredient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Cacheable(value = "ingredient")
    @Query("SELECT i FROM Ingredient i WHERE i.ingredientType = 'ingredient'")
    List<Ingredient> findAll();

    @Cacheable(value = "ingredient", key = "#id")
    Optional<Ingredient> findById(Long id);

    @CachePut(value = "ingredient", key = "#ingredient.id")
    Ingredient save(Ingredient ingredient);

    @CacheEvict(value = "ingredient", key = "#id")
    void deleteById(Long id);
}