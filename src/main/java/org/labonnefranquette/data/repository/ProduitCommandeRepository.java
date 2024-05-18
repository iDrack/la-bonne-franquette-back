package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitCommandeRepository extends JpaRepository<ProduitCommande, Long> {
}
