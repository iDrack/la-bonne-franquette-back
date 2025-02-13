package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.PaiementTypeCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementTypeCommandeRepository extends JpaRepository<PaiementTypeCommande, Long> {

}