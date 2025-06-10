package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("integration")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Resto Users");
        restaurant.setMenuVersion(1);
        restaurant.setIsTVAEnable(true);
        restaurantRepository.save(restaurant);
    }

    @Test
    void saveAndFindById() {
        // Given
        User user = new User();
        user.setUsername("franfran");
        user.setPassword("securepassword");
        user.setRoles(Roles.ROLE_ADMIN);
        user.setRestaurant(restaurant);

        // When
        User saved = userRepository.save(user);

        // Then
        Optional<User> maybe = userRepository.findById(saved.getId());
        assertThat(maybe).isPresent();
        User found = maybe.get();
        assertThat(found.getUsername()).isEqualTo("franfran");
        assertThat(found.getPassword()).isEqualTo("securepassword");
        assertThat(found.getRoles()).contains("ROLE_ADMIN");
        assertThat(found.getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void findByUsernameReturnsCorrectUser() {
        // Given
        User user = new User();
        user.setUsername("chef");
        user.setPassword("motdepasse");
        user.setRoles(Roles.ROLE_USER);
        user.setRestaurant(restaurant);
        userRepository.save(user);

        // When
        User found = userRepository.findByUsername("chef");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("chef");
        assertThat(found.getRoles()).contains("ROLE_USER");
    }

    @Test
    void existsByUsernameWorks() {
        // Given
        User user = new User();
        user.setUsername("employe");
        user.setPassword("secret");
        user.setRoles(Roles.ROLE_USER);
        user.setRestaurant(restaurant);
        userRepository.save(user);

        // When & Then
        assertThat(userRepository.existsByUsername("employe")).isTrue();
        assertThat(userRepository.existsByUsername("autre")).isFalse();
    }

    @Test
    void deleteByIdRemovesEntity() {
        // Given
        User user = new User();
        user.setUsername("delete_me");
        user.setPassword("password");
        user.setRoles(Roles.ROLE_USER);
        user.setRestaurant(restaurant);
        User saved = userRepository.save(user);

        // When
        userRepository.deleteById(saved.getId());

        // Then
        Optional<User> maybe = userRepository.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }

    @Test
    void setAndResetRolesWorks() {
        // Given
        User user = new User();
        user.setUsername("multi");
        user.setPassword("pass");
        user.setRoles(Roles.ROLE_USER);
        user.setRestaurant(restaurant);
        User saved = userRepository.save(user);

        // When
        saved.setRoles(Roles.ROLE_MANAGER);
        userRepository.save(saved);

        // Then
        User found = userRepository.findByUsername("multi");
        assertThat(found.getRoles()).contains("ROLE_USER", "ROLE_MANAGER");

        // When: reset roles
        found.resetRoles();
        userRepository.save(found);
        User afterReset = userRepository.findByUsername("multi");
        assertThat(afterReset.getRoles()).isEmpty();
    }
}
