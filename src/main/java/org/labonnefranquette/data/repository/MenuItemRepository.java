package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.MenuItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Cacheable(value = "menu_item")
    List<MenuItem> findAll();

    @Cacheable(value = "menu_item", key = "#id")
    Optional<MenuItem> findById(Long id);

    @CachePut(value = "menu_item", key = "#menu_item.id")
    MenuItem save(MenuItem menuItem);

    @CacheEvict(value = "menu_item", key = "#id")
    void deleteById(Long id);
}
