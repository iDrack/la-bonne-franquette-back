package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAll();
    Optional<Ingredient> findById(Long id);
    Ingredient save(Ingredient ingredient);
    void deleteById(Long id);
}