package org.example.labonnefranquette.repository;

import org.example.labonnefranquette.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
