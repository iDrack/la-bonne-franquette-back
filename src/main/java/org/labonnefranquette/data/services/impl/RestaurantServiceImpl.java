package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.repository.RestaurantRepository;
import org.labonnefranquette.data.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository repository;

    @Override
    public Optional<Restaurant> findAllById(Long id) {
        return repository.findById(id);
    }

    @Override
    public int getVersion(Long idRestaurant) {
        return findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver le restaurant : " + idRestaurant)).getVersionCarte();
    }

    @Override
    public void updateCacheVersion(Long idRestaurant) {
        Restaurant restaurant = findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver le restaurant : " + idRestaurant));
        restaurant.updateVersionCarte();
        repository.save(restaurant);
    }


}
