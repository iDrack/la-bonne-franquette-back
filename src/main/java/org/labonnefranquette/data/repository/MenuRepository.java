package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAll();
    Optional<Menu> findById(Long id);
    Menu save(Menu menu);
    void deleteById(Long id);
}
