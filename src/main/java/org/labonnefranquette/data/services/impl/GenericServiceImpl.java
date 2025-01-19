package org.labonnefranquette.data.services.impl;


import org.labonnefranquette.data.cache.CacheService;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T, U extends JpaRepository<T, ID>, ID> implements GenericService<T, ID> {

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
    public T create(T newT) {
        CacheService.changeCacheVersion();
        return repository.save(newT);
    }

    @Override
    public void deleteById(ID id) {
        CacheService.changeCacheVersion();
        repository.deleteById(id);
    }
}