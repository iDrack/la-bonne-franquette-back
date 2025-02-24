package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Cache;
import org.labonnefranquette.data.repository.CacheRepository;
import org.labonnefranquette.data.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    CacheRepository cacheRepository;

    @Override
    public int getVersion(Long idRestaurant) {
        return cacheRepository.getByRestaurantId(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de récupérer le cache.")).getVersion();
    }

    @Override
    public void updateCacheVersion(Long idRestaurant) {
        Cache cache = cacheRepository.getByRestaurantId(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de récupérer le cache."));
        cache.setVersion(cache.getVersion() + 1);
        cacheRepository.save(cache);
    }
}
