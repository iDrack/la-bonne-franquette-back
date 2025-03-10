package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PaiementRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaiementRepository paiementRepository;

    @BeforeEach
    public void setup() {
        Commande commande = new Commande();
        commande.setSurPlace(false);
        commande.setDateSaisie(new Date());
        commande.setStatus(StatusCommande.EN_COURS);
        entityManager.persistAndFlush(commande);
        Paiement paiement = new Paiement();
        paiement.setType("CB");
        paiement.setCommande(commande);
        paiement.setDate(new Date());
        entityManager.persistAndFlush(paiement);
    }

    @Test
    public void findByCommandeId_returnsPaiementsForGivenCommandeId() {
        Commande commande = new Commande();
        commande.setSurPlace(false);
        commande.setDateSaisie(new Date());
        commande.setStatus(StatusCommande.EN_COURS);
        entityManager.persistAndFlush(commande);
        Paiement paiementTest = new Paiement();
        paiementTest.setCommande(commande);
        paiementTest.setType("AUTRE");
        paiementTest.setDate(new Date());
        entityManager.persistAndFlush(paiementTest);
        Optional<List<Paiement>> paiements = paiementRepository.findByCommandeId(paiementTest.getCommande().getId());
        assertThat(paiements.isPresent()).isTrue();
        assertThat(paiements.get()).hasSize(1);
    }

    @Test
    public void findByCommandeId_returnsEmptyForNonExistingCommandeId() {
        Optional<List<Paiement>> paiements = paiementRepository.findByCommandeId(999L);
        Assertions.assertEquals(0, paiements.get().size());
    }
}