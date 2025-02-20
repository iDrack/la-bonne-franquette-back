package org.labonnefranquette.data.services;

public interface CacheService {

    public int getVersion();

    public void updateCacheVersion();
}
