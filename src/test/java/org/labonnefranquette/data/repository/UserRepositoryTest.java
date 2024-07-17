package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setRoles(Roles.ROLE_USER);
        user.setPassword("test");
        user.setUsername("testUser");
        entityManager.persistAndFlush(user);
    }

    @Test
    public void existsByUsername_returnsTrueForExistingUser() {
        Boolean exists = userRepository.existsByUsername("testUser");
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByUsername_returnsFalseForNonExistingUser() {
        Boolean exists = userRepository.existsByUsername("nonExistingUser");
        assertThat(exists).isFalse();
    }

    @Test
    public void findByUsername_returnsCorrectUser() {
        User user = userRepository.findByUsername("testUser");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
    }

    @Test
    public void findByUsername_returnsNullForNonExistingUser() {
        User user = userRepository.findByUsername("nonExistingUser");
        assertThat(user).isNull();
    }
}