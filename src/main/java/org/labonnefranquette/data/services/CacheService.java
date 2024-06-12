package org.labonnefranquette.data.services;

public interface CacheService {

    public void incrementCacheVersion();
    public String getVersion();
}
