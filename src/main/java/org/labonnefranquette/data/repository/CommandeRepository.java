package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query(value = "SELECT c.numero as numero, c.dateSaisie as dateSaisie, c.nbArticle as nbArticle, c.prixHT as prixHT, c.status as status, c.paiementType as paiementType FROM Commande c")
    List<CommandeListeProjection> findAllCommandeListe();
}
