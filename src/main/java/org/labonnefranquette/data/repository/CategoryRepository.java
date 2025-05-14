package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category categorie);
    void deleteById(Long id);
}
