package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Produit;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    List<T> findAll();
    Optional<T> findAllById(ID id);
    T create(T newT);
    void deleteById(ID id);


}
