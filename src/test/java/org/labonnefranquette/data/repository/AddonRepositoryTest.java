package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.TauxTVA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Tag("integration")
class AddonRepositoryTest {

    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Mon Resto");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    @DisplayName("save() + findById() : un Addon sauvegardé se retrouve correctement")
    void saveAndFindById() {
        // Given : construction d’un Addon complet
        Addon addon = new Addon();
        addon.setName("Sauce Fromage");
        addon.setTauxTVA(TauxTVA.NORMAL);
        addon.setPrixHT(250);
        addon.setRestaurant(restaurant);

        // When : on sauvegarde dans la base H2
        Addon saved = addonRepository.save(addon);

        // Then : on le récupère via findById
        Optional<Addon> maybe = addonRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Addon found = maybe.get();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getTauxTVA()).isEqualTo(saved.getTauxTVA());
        assertThat(found.getPrixHT()).isEqualTo(250);
        assertThat(found.getTotalPrice()).isEqualTo(saved.getTotalPrice());
        assertThat(found.getRestaurant().getId()).isEqualTo(saved.getRestaurant().getId());
    }

    @Test
    @DisplayName("findAll() doit retourner l’ensemble des Addons présents")
    void findAllReturnsAll() {
        // Given : trois Addons en base, chacun lié au même restaurant
        Addon a1 = new Addon();
        a1.setName("Extra Ketchup");
        a1.setTauxTVA(TauxTVA.NORMAL);
        a1.setPrixHT(100);
        a1.setRestaurant(restaurant);
        addonRepository.save(a1);

        Addon a2 = new Addon();
        a2.setName("Bacon Croquant");
        a2.setTauxTVA(TauxTVA.AUCUN);
        a2.setPrixHT(300);
        a2.setRestaurant(restaurant);
        addonRepository.save(a2);

        Addon a3 = new Addon();
        a3.setName("Nuggets");
        a3.setTauxTVA(TauxTVA.INTERMEDIAIRE);
        a3.setPrixHT(200);
        a3.setRestaurant(restaurant);
        addonRepository.save(a3);

        // When : on appelle findAll()
        List<Addon> liste = addonRepository.findAll();

        // Then : on doit retrouver exactement 3 éléments
        assertThat(liste)
                .hasSize(3)
                .extracting(Addon::getName)
                .containsExactlyInAnyOrder("Extra Ketchup", "Bacon Croquant", "Nuggets");
    }

    @Test
    @DisplayName("deleteById() supprime correctement si l’ID existe")
    void deleteByIdRemovesEntity() {
        // Given : un Addon en base
        Addon toRemove = new Addon();
        toRemove.setName("Oignons Frits");
        toRemove.setTauxTVA(TauxTVA.NORMAL);
        toRemove.setPrixHT(150);
        toRemove.setRestaurant(restaurant);
        Addon saved = addonRepository.save(toRemove);

        // When : suppression par ID
        addonRepository.deleteById(saved.getId());

        // Then : findById() ne doit rien retourner pour cet ID
        Optional<Addon> maybe = addonRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
