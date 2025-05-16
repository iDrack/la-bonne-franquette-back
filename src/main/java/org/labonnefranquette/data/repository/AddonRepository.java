package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Addon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddonRepository extends JpaRepository<Addon, Long> {

    List<Addon> findAll();
    Optional<Addon> findById(Long id);
    Addon save(Addon addon);
    void deleteById(Long id);
}
