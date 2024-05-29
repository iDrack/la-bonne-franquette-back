package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.Produit;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Cacheable(value = "menu")
    List<Menu> findAll();
    @Cacheable(value = "menu", key = "#id")
    Optional<Menu> findById(Long id);
    @CachePut(value = "menu", key = "#menu.id")
    Menu save(Menu menu);
    @CacheEvict(value = "menu", key = "#id")
    void deleteById(Long id);
}
