package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query(value = "SELECT c.id as id, c.numero as numero, c.dateSaisie as dateSaisie, c.dateLivraison as dateLivraison, c.surPlace as surPlace, c.paye as paye, c.prixTTC as prixTTC, c.status as status, c.paiementType as paiementType FROM Commande c WHERE DATE(c.dateSaisie) = :date ORDER BY c.dateSaisie DESC")
    List<CommandeListeProjection> findAllCommandeListe(@Param("date") Date date);

    @Query(value = "SELECT c FROM Commande c WHERE c.status = :status")
    List<Commande> findAllCommandeWithStatut(@Param("status") StatusCommande status);
}
