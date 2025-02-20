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
    public int getVersion() {
        return cacheRepository.findById(1L).orElseThrow(() -> new RuntimeException("Impossible de récupérer le cache.")).getVersion();
    }

    @Override
    public void updateCacheVersion() {
        Cache cache = cacheRepository.findById(1L).orElseThrow(() -> new RuntimeException("Impossible de récupérer le cache."));
        cache.setVersion(cache.getVersion() + 1);
        cacheRepository.save(cache);
    }
}
