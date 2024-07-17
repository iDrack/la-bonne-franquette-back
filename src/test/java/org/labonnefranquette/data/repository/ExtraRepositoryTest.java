package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Extra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ExtraRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExtraRepository extraRepository;

    @BeforeEach
    public void setup() {
        Extra extra = new Extra();
        extra.setNom("Extra Cheese");
        entityManager.persistAndFlush(extra);
    }

    @Test
    public void findAll_returnsAllExtras() {
        List<Extra> extras = extraRepository.findAll();
        assertThat(extras).hasSize(1);
        assertThat(extras.get(0).getNom()).isEqualTo("Extra Cheese");
    }

    @Test
    public void findById_returnsCorrectExtra() {
        Extra extraTest = new Extra();
        extraTest.setNom("test");
        entityManager.persistAndFlush(extraTest);
        Optional<Extra> extra = extraRepository.findById(extraTest.getId());
        assertThat(extra.isPresent()).isTrue();
        assertThat(extra.get().getNom()).isEqualTo("test");
    }

    @Test
    public void findById_returnsEmptyForNonExistingId() {
        Optional<Extra> extra = extraRepository.findById(999L);
        assertThat(extra.isPresent()).isFalse();
    }

    @Test
    public void save_savesAndReturnsExtra() {
        Extra newExtra = new Extra();
        newExtra.setNom("Extra Bacon");
        Extra savedExtra = extraRepository.save(newExtra);
        assertThat(savedExtra).isNotNull();
        assertThat(savedExtra.getNom()).isEqualTo("Extra Bacon");
    }

    @Test
    public void deleteById_deletesExtra() {
        extraRepository.deleteById(1L);
        Optional<Extra> extra = extraRepository.findById(1L);
        assertThat(extra.isPresent()).isFalse();
    }
}