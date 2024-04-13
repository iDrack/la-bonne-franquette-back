package org.example.labonnefranquette.repository;

import org.example.labonnefranquette.model.TauxTVA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TauxTVARepository extends JpaRepository<TauxTVA, Long> {
}
