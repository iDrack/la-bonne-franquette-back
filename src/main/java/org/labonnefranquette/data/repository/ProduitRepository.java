package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.model.Produit;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Cacheable(value = "produit")
    List<Produit> findAll();
    @Cacheable(value = "produit", key = "#id")
    Optional<Produit> findById(Long id);
    @CachePut(value = "produit", key = "#produit.id")
    Produit save(Produit produit);
    @CacheEvict(value = "produit", key = "#id")
    void deleteById(Long id);
}
