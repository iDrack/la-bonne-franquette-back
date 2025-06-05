package org.labonnefranquette.data.services.impl;


import org.labonnefranquette.data.model.interfaces.HasRestaurant;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.GenericService;
import org.labonnefranquette.data.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GenericServiceImpl<T extends HasRestaurant, U extends JpaRepository<T, ID>, ID> implements GenericService<T, ID> {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private JWTUtil jwtUtil;

    private final U repository;

    public GenericServiceImpl(U repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> getAllById(ID id) {
        return repository.findById(id);
    }


    @Override
    public Optional<T> getByName(String name, Long restaurantId) {
        if (!existsByName(name, restaurantId)) return Optional.empty();

        return repository.findAll().stream()
                .filter(item -> item.getName().equalsIgnoreCase(name) && item.getRestaurant().getId().equals(restaurantId))
                .findFirst();
    }

    @Override
    public Optional<T> deleteById(ID id) {
        Optional<T> itemFound = repository.findById(id);
        if (itemFound.isPresent()) {
            T item = itemFound.get();
            Long restaurantId = item.getRestaurant().getId();
            restaurantService.updateCacheVersion(restaurantId);
            repository.deleteById(id);
        }
        return itemFound;
    }

    @Override
    public List<T> getAll(String token) throws NullPointerException {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        return repository.findAll().stream().filter((x) -> Objects.equals(x.getRestaurant().getId(), idRestaurant)).toList();
    }

    @Override
    public T create(T newT, String token) {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        newT.setRestaurant(restaurantService.findAllById(idRestaurant).orElseThrow(() -> new NullPointerException(String.format("Impossible de trouver de restaurant avec l'ID %s.", idRestaurant))));
        restaurantService.updateCacheVersion(idRestaurant);
        return repository.save(newT);
    }

    @Override
    public T update(ID id, T newT, String token) throws NullPointerException {
        Long idRestaurant = jwtUtil.extractRestaurantId(token);
        getAllById(id).orElseThrow(() -> new NullPointerException(String.format("L'ID %s n'éxiste pas pour un %s.", id, newT.getClass().getSimpleName())));
        newT.setRestaurant(restaurantService.findAllById(idRestaurant).orElseThrow(() -> new NullPointerException(String.format("Impossible de trouver de restaurant avec l'ID %s.", idRestaurant))));
        restaurantService.updateCacheVersion(idRestaurant);
        return repository.save(newT);
    }

    @Override
    public boolean existsByName(String name, Long restaurantId) {
        return repository.findAll().stream().anyMatch(item -> item.getName().equalsIgnoreCase(name) && !item.getRestaurant().getId().equals(restaurantId));
    }

}
