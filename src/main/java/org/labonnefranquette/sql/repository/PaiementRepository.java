package org.labonnefranquette.sql.repository;

import org.labonnefranquette.sql.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Optional<List<Paiement>> findByCommandeId(Long commandeId);
}
