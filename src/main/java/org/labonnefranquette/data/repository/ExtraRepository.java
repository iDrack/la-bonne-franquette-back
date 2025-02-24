package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {

    List<Extra> findAll();
    Optional<Extra> findById(Long id);
    Extra save(Extra extra);
    void deleteById(Long id);
}
