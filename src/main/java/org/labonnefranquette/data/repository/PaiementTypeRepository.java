package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.PaiementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementTypeRepository extends JpaRepository<PaiementType, Long> {
    PaiementType findByName(String name);
}