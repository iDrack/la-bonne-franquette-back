package org.labonnefranquette.data.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CacheRepositoryTest {

    @Autowired
    private CacheRepository cacheRepository;

    @Test
    void getFirstByIdReturnsCacheWhenIdExists() {
        Cache cache = new Cache();
        cache.setId(1L);
        cacheRepository.save(cache);

        Cache foundCache = cacheRepository.getFirstById(1L);

        assertThat(foundCache).isNotNull();
        assertThat(foundCache.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstByIdReturnsNullWhenIdDoesNotExist() {
        Cache foundCache = cacheRepository.getFirstById(999L);

        assertThat(foundCache).isNull();
    }
}