package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.projection.CommandeListeProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CommandeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandeRepository commandeRepository;

    @BeforeEach
    public void setup() {
        Commande commande = new Commande();
        commande.setNumero((short) 123);
        commande.setStatus(StatusCommande.EN_COURS);
        commande.setDateSaisie(new Date());
        commande.setSurPlace(true);
        entityManager.persistAndFlush(commande);
    }

    @Test
    public void findAllCommandeListe_returnsAllCommandes() {
        List<CommandeListeProjection> commandes = commandeRepository.findAllCommandeListe();
        assertThat(commandes).hasSize(1);
        assertThat(commandes.get(0).getNumero()).isEqualTo((short) 123);
    }

    @Test
    public void findAllCommandeWithStatut_returnsCommandesWithGivenStatus() {
        List<Commande> commandes = commandeRepository.findAllCommandeWithStatut(StatusCommande.EN_COURS);
        assertThat(commandes).hasSize(1);
        assertThat(commandes.get(0).getNumero()).isEqualTo((short) 123);
    }

    @Test
    public void findAllCommandeWithStatut_returnsEmptyListForStatusWithNoCommandes() {
        List<Commande> commandes = commandeRepository.findAllCommandeWithStatut(StatusCommande.TERMINEE);
        assertThat(commandes).isEmpty();
    }
}
