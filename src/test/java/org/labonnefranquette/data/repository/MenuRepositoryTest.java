package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class MenuRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    public void setup() {
        Menu menu = new Menu();
        menu.setNom("Menu 1");
        entityManager.persistAndFlush(menu);
    }

    @Test
    public void findAll_returnsAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertThat(menus.get(0).getNom()).isEqualTo("Menu 1");
    }

    @Test
    public void findById_returnsCorrectMenu() {
        Menu menuTest = new Menu();
        menuTest.setNom("Menu test");
        entityManager.persistAndFlush(menuTest);
        Optional<Menu> menu = menuRepository.findById(menuTest.getId());
        assertThat(menu.isPresent()).isTrue();
        assertThat(menu.get().getNom()).isEqualTo("Menu test");
    }

    @Test
    public void findById_returnsEmptyForNonExistingId() {
        Optional<Menu> menu = menuRepository.findById(999L);
        assertThat(menu.isPresent()).isFalse();
    }

    @Test
    public void save_savesAndReturnsMenu() {
        Menu newMenu = new Menu();
        newMenu.setNom("Menu 2");
        Menu savedMenu = menuRepository.save(newMenu);
        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getNom()).isEqualTo("Menu 2");
    }

    @Test
    public void deleteById_deletesMenu() {
        menuRepository.deleteById(1L);
        Optional<Menu> menu = menuRepository.findById(1L);
        assertThat(menu.isPresent()).isFalse();
    }
}