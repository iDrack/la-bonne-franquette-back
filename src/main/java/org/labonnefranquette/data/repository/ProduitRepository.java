package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findAll();
    Optional<Produit> findById(Long id);
    Produit save(Produit produit);
    void deleteById(Long id);
}
