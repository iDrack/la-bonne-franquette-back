package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.repository.RestaurantRepository;
import org.labonnefranquette.data.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Optional<Restaurant> findAllById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public int getVersion(Long idRestaurant) {
        return findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver le restaurant : " + idRestaurant)).getVersionCarte();
    }

    @Override
    public void updateCacheVersion(Long idRestaurant) {
        Restaurant restaurant = findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver le restaurant : " + idRestaurant));
        restaurant.updateVersionCarte();
        restaurantRepository.save(restaurant);
    }

    /**
     * Créer un nouveau restaurant
     *
     * @param restaurantName Nom du restaurant à créer
     * @return Le nouveau restaurant
     * @throws IllegalArgumentException si le nom de restaurant est déjà pris
     */
    @Override
    public Restaurant createRestaurant(String restaurantName) throws IllegalArgumentException {
        if (Boolean.TRUE.equals(this.restaurantRepository.existsByName(restaurantName))) {
            throw new IllegalArgumentException("Impossible de créer ce nouveau restaurant: Le nom  : '" + restaurantName + "' éxiste déjà.");
        }
        if (restaurantName == null || restaurantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Impossible de créer ce nouveau restaurant: Le nom du restaurant ne peut pas être vide.");
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        restaurantRepository.save(restaurant);

        return restaurant;
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    /**
     * Ajoute un nouvel utilisateur à la liste des employés d'unn restaurant
     *
     * @param restaurant Le restaurant à modifier
     * @param user       L'utilisateur à ajouter au restaurant
     * @return Le restaurant modifié
     */
    @Override
    public Restaurant addUserToRestaurant(Restaurant restaurant, User user) {
        if (restaurant.getEmployees() == null) {
            restaurant.setEmployees(new ArrayList<>(Collections.singletonList(user)));
        } else {
            restaurant.getEmployees().add(user);
        }
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public List<User> getAllUserFromRestaurant(Long idRestaurant) throws NullPointerException {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new NullPointerException("Impossible de trouver le restaurant avec l'id : " + idRestaurant));
        return restaurant.getEmployees();
    }
}
