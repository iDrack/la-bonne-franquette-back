package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.model.SousCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CategorieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategorieRepository categorieRepository;

    @BeforeEach
    public void setup() {
        Categorie categorie = new Categorie();
        categorie.setCategorieType("categorie");
        categorie.setNom("test");
        entityManager.persistAndFlush(categorie);

        Categorie sousCategorie = new SousCategorie();
        sousCategorie.setNom("test");
        sousCategorie.setCategorieType("sous-categorie");
        entityManager.persistAndFlush(sousCategorie);
    }

    @Test
    public void findAll_returnsAllCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        assertThat(categories).hasSize(2);
    }

    @Test
    public void findAllCategorie_returnsOnlyCategories() {
        List<Categorie> categories = categorieRepository.findAllCategorie();
        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getCategorieType()).isEqualTo("categorie");
    }

    @Test
    public void findAllSousCategorie_returnsOnlySousCategories() {
        List<Categorie> categories = categorieRepository.findAllSousCategorie();
        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getCategorieType()).isEqualTo("sous-categorie");
    }

    @Test
    public void findById_returnsCorrectCategorie() {
        Categorie categorieTest = new Categorie();
        categorieTest.setCategorieType("categorie");
        categorieTest.setNom("test-id");
        entityManager.persistAndFlush(categorieTest);
        Optional<Categorie> categorieFound = categorieRepository.findById(categorieTest.getId());
        assertThat(categorieFound.isPresent()).isTrue();
        assertThat(categorieFound.get().getNom()).isEqualTo("test-id");
    }

    @Test
    public void save_savesAndReturnsNewItem() {
        Categorie newCategorie = new Categorie();
        newCategorie.setCategorieType("categorie");
        newCategorie.setNom("new-item");
        Categorie savedCategorie = categorieRepository.save(newCategorie);
        assertThat(savedCategorie).isNotNull();
        assertThat(savedCategorie.getNom()).isEqualTo("new-item");
    }

    @Test
    public void deleteById_deletesCategorie() {
        categorieRepository.deleteById(1L);
        Optional<Categorie> categorie = categorieRepository.findById(1L);
        assertThat(categorie.isPresent()).isFalse();
    }
}