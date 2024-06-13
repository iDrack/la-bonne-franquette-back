package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.services.CacheService;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService{

    private static int cacheVersion = 0;

    public void incrementCacheVersion() {
        cacheVersion++;
    }
    public String getVersion() {
        return String.valueOf(cacheVersion);
    }
}
