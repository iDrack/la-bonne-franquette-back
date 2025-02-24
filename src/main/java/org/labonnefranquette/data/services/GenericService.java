package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends HasRestaurant, ID> {
    List<T> findAll();
    Optional<T> findAllById(ID id);
    void deleteById(ID id);

    // manipulation des composants de la carte d'un restaurant
    List<T> findAll(String token);

    T create(T newT, String token);
}
