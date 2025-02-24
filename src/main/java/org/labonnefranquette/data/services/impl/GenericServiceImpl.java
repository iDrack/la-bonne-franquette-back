package org.labonnefranquette.data.services.impl;


import org.labonnefranquette.data.model.interfaces.HasRestaurant;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.CacheService;
import org.labonnefranquette.data.services.GenericService;
import org.labonnefranquette.data.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GenericServiceImpl<T extends HasRestaurant, U extends JpaRepository<T, ID>, ID> implements GenericService<T, ID> {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private JWTUtil jwtUtil;

    private final U repository;

    public GenericServiceImpl(U repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findAllById(ID id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(ID id) {
        Optional<T> itemFound = repository.findById(id);
        if (itemFound.isPresent()) {
            T item = itemFound.get();
            Long restaurantId = item.getRestaurant().getId();
            cacheService.updateCacheVersion(restaurantId);
            repository.deleteById(id);
        }
    }

    @Override
    public List<T> findAll(String token) {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        return findAll().stream().filter((x) -> Objects.equals(x.getRestaurant().getId(), idRestaurant)).toList();
    }

    @Override
    public T create(T newT, String token) {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        newT.setRestaurant(restaurantService.findAllById(idRestaurant).orElseThrow(() -> new RuntimeException("Impossible de trouver de restaurant : " + idRestaurant)));
        cacheService.updateCacheVersion(idRestaurant);
        return repository.save(newT);
    }

}