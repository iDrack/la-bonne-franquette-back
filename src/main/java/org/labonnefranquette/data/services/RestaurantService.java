package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    Optional<Restaurant> findAllById(Long id);

    public int getVersion(Long idRestaurant);

    public void updateCacheVersion(Long idRestaurant);

    public Restaurant create(String restaurantName) throws IllegalArgumentException;

    public void delete(Restaurant restaurant);

    public Restaurant addUser(Restaurant restaurant, User user);

    public List<User> getAllUser(Long idRestaurant) throws NullPointerException;
}
