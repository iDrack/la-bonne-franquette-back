package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Cache;
import org.labonnefranquette.data.repository.CacheRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CacheServiceImplTest {

    @Mock
    private CacheRepository cacheRepository;

    @InjectMocks
    private CacheServiceImpl cacheService;

    @Test
    public void getVersionWithValidRestaurantId() {
        Cache cache = new Cache();
        cache.setVersion(1);
        when(cacheRepository.getByRestaurantId(1L)).thenReturn(Optional.of(cache));

        int version = cacheService.getVersion(1L);

        assertEquals(1, version);
    }

    @Test
    public void getVersionWithInvalidRestaurantId() {
        when(cacheRepository.getByRestaurantId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cacheService.getVersion(1L);
        });

        assertEquals("Impossible de récupérer le cache.", exception.getMessage());
    }

    @Test
    public void updateCacheVersionWithValidRestaurantId() {
        Cache cache = new Cache();
        cache.setVersion(1);
        when(cacheRepository.getByRestaurantId(1L)).thenReturn(Optional.of(cache));

        cacheService.updateCacheVersion(1L);

        verify(cacheRepository).save(cache);
        assertEquals(2, cache.getVersion());
    }

    @Test
    public void updateCacheVersionWithInvalidRestaurantId() {
        when(cacheRepository.getByRestaurantId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cacheService.updateCacheVersion(1L);
        });

        assertEquals("Impossible de récupérer le cache.", exception.getMessage());
    }
}