package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findAll();
    @Query("SELECT c FROM Categorie c WHERE c.categorieType = 'categorie'")
    List<Categorie> findAllCategorie();
    @Query("SELECT c FROM Categorie c WHERE c.categorieType = 'sous-categorie'")
    List<Categorie> findAllSousCategorie();
    Optional<Categorie> findById(Long id);
    Categorie save(Categorie categorie);
    void deleteById(Long id);
}
