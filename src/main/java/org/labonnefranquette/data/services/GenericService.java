package org.labonnefranquette.data.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    List<T> findAll();

    Optional<T> findAllById(ID id);

}