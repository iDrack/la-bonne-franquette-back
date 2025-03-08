package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class MenuItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @BeforeEach
    public void setup() {
        menuItem1 = new MenuItem();
        menuItem1.setOptional(true);
        menuItem1.setPrixHT(50);
        entityManager.persistAndFlush(menuItem1);

        menuItem2 = new MenuItem();
        menuItem2.setOptional(false);
        menuItem2.setPrixHT(100);
        entityManager.persistAndFlush(menuItem2);
    }

    @Test
    public void findAll_returnsAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertThat(menuItems).hasSize(2);
        assertThat(menuItems).contains(menuItem1, menuItem2);
    }

    @Test
    public void findById_returnsCorrectMenuItem() {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItem1.getId());
        assertThat(menuItem.isPresent()).isTrue();
        assertThat(menuItem.get()).isEqualTo(menuItem1);
    }

    @Test
    public void findById_returnsEmptyForNonExistingId() {
        Optional<MenuItem> menuItem = menuItemRepository.findById(999L);
        assertThat(menuItem.isPresent()).isFalse();
    }

    @Test
    public void deleteById_deletesMenuItem() {
        menuItemRepository.deleteById(menuItem1.getId());
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItem1.getId());
        assertThat(menuItem.isPresent()).isFalse();
    }
}

