package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends HasRestaurant, ID> {
    Optional<T> getAllById(ID id);

    Optional<T> getByName(String name, Long restaurantId);
    Optional<T> deleteById(ID id);
    List<T> getAll(String token);

    T create(T newT, String token) throws NullPointerException;

    T update(ID id, T updatedT, String token) throws NullPointerException;

    boolean existsByName(String name, Long restaurantId);
}
