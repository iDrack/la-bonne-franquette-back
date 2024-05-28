package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.model.Ingredient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Cacheable(value = "categorie")
    List<Categorie> findAll();
    @Cacheable(value = "categorie", key = "#id")
    Optional<Categorie> findById(Long id);
    @CachePut(value = "categorie", key = "#categorie.id")
    Categorie save(Categorie categorie);
    @CacheEvict(value = "categorie", key = "#id")
    void deleteById(Long id);
}
