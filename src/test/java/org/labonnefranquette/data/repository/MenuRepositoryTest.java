package org.labonnefranquette.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.VATRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private EntityManager em;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("MenuTest Resto");
        restaurant.setIsTVAEnable(true);
        em.persist(restaurant);
        em.flush();
    }

    @Test
    void saveAndFindById() {
        // Given
        MenuItem item = new MenuItem();
        item.setPrice(300);
        item.setOptional(false);
        item.setVATRate(VATRate.NORMAL);
        item.setRestaurant(restaurant);

        Menu menu = new Menu();
        menu.setName("Menu Enfant");
        menu.setPrice(990);
        menu.setVATRate(VATRate.NORMAL);
        menu.setRestaurant(restaurant);
        menu.setMenuItems(List.of(item));

        // When
        Menu saved = menuRepository.save(menu);

        // Then
        Optional<Menu> maybe = menuRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        Menu found = maybe.get();
        assertThat(found.getName()).isEqualTo("Menu Enfant");
        assertThat(found.getPrice()).isEqualTo(990);
        assertThat(found.getVATRate()).isEqualTo(VATRate.NORMAL);
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(found.getMenuItems()).hasSize(1);
        assertThat(found.getMenuItems().get(0).getId()).isEqualTo(item.getId());
    }

    @Test
    void findAllReturnsAll() {
        // Given
        Menu m1 = new Menu();
        m1.setName("Menu Solo");
        m1.setPrice(700);
        m1.setVATRate(VATRate.NORMAL);
        m1.setRestaurant(restaurant);

        Menu m2 = new Menu();
        m2.setName("Menu Duo");
        m2.setPrice(1300);
        m2.setVATRate(VATRate.NORMAL);
        m2.setRestaurant(restaurant);

        menuRepository.saveAll(Arrays.asList(m1, m2));

        // When
        List<Menu> liste = menuRepository.findAll();

        // Then
        assertThat(liste)
                .hasSize(2)
                .extracting(Menu::getName)
                .containsExactlyInAnyOrder("Menu Solo", "Menu Duo");
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        Menu menu = new Menu();
        menu.setName("Menu à Supprimer");
        menu.setPrice(999);
        menu.setVATRate(VATRate.NORMAL);
        menu.setRestaurant(restaurant);
        Menu saved = menuRepository.save(menu);

        // When
        menuRepository.deleteById(saved.getId());

        // Then
        Optional<Menu> maybe = menuRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
