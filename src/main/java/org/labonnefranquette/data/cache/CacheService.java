package org.labonnefranquette.data.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CacheService {

    private static String cacheVersion = UUID.randomUUID().toString();
    public static void changeCacheVersion() {
        cacheVersion = UUID.randomUUID().toString();
    }
    public static String getVersion() {
        return cacheVersion;
    }

    @Autowired
    CachePreloader cachePreloader;
    @CacheEvict(value = {"categorie", "extra", "ingredient", "menu", "menu_item", "produit"}, allEntries = true)
    public boolean clear() {
        try {
            cachePreloader.run();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}