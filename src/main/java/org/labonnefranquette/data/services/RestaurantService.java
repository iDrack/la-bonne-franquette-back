package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;

import java.util.Optional;

public interface RestaurantService {
    Optional<Restaurant> findAllById(Long id);

    public int getVersion(Long idRestaurant);

    public void updateCacheVersion(Long idRestaurant);

    public Restaurant createRestaurant(String restaurantName) throws IllegalArgumentException;

    public void deleteRestaurant(Restaurant restaurant);

    public Restaurant addUserToRestaurant(Restaurant restaurant, User user);
}
