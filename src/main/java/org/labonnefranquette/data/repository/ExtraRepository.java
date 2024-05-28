package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.model.Ingredient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {

    @Cacheable(value = "extra")
    List<Extra> findAll();

    @Cacheable(value = "extra", key = "#id")
    Optional<Extra> findById(Long id);

    @CachePut(value = "extra", key = "#extra.id")
    Extra save(Extra extra);

    @CacheEvict(value = "extra", key = "#id")
    void deleteById(Long id);
}
