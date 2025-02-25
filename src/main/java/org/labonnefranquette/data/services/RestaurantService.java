package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Restaurant;

import java.util.Optional;

public interface RestaurantService {
    Optional<Restaurant> findAllById(Long id);

    public int getVersion(Long idRestaurant);

    public void updateCacheVersion(Long idRestaurant);
}
