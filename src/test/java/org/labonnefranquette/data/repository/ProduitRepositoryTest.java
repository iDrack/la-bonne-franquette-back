package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProduitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProduitRepository produitRepository;

    @BeforeEach
    public void setup() {
        Produit produit = new Produit();
        produit.setNom("Produit 1");
        entityManager.persistAndFlush(produit);
    }

    @Test
    public void findAll_returnsAllProduits() {
        List<Produit> produits = produitRepository.findAll();
        assertThat(produits).hasSize(1);
        assertThat(produits.get(0).getNom()).isEqualTo("Produit 1");
    }

    @Test
    public void findById_returnsCorrectProduit() {
        Produit produitTest = new Produit();
        produitTest.setNom("test");
        entityManager.persistAndFlush(produitTest);
        Optional<Produit> produit = produitRepository.findById(produitTest.getId());
        assertThat(produit.isPresent()).isTrue();
        assertThat(produit.get().getNom()).isEqualTo("test");
    }

    @Test
    public void findById_returnsEmptyForNonExistingId() {
        Optional<Produit> produit = produitRepository.findById(999L);
        assertThat(produit.isPresent()).isFalse();
    }

    @Test
    public void save_savesAndReturnsProduit() {
        Produit newProduit = new Produit();
        newProduit.setNom("Produit 2");
        Produit savedProduit = produitRepository.save(newProduit);
        assertThat(savedProduit).isNotNull();
        assertThat(savedProduit.getNom()).isEqualTo("Produit 2");
    }

    @Test
    public void deleteById_deletesProduit() {
        produitRepository.deleteById(1L);
        Optional<Produit> produit = produitRepository.findById(1L);
        assertThat(produit.isPresent()).isFalse();
    }
}