package org.labonnefranquette.data.services;

public interface CacheService {

    public int getVersion(Long idRestaurant);

    public void updateCacheVersion(Long idRestaurant);
}
