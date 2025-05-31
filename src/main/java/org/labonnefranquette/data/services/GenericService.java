package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends HasRestaurant, ID> {
    Optional<T> getAllById(ID id);

    Optional<T> deleteById(ID id);
    List<T> getAll(String token);
    T create(T newT, String token);

    boolean existsByName(String name);
}
