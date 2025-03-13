package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends HasRestaurant, ID> {
    Optional<T> findAllById(ID id);
    void deleteById(ID id);

    List<T> findAll(String token);

    T create(T newT, String token);
}


