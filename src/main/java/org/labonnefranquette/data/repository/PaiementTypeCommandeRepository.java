package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.entity.PaiementTypeCommandeEntity;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementTypeCommandeRepository extends JpaRepository<PaiementTypeCommandeEntity, Long> {
    boolean existsByType(PaiementTypeCommande type);

}