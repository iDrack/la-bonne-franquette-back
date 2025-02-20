package org.labonnefranquette.data.services.impl;


import org.labonnefranquette.data.services.CacheService;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T, U extends JpaRepository<T, ID>, ID> implements GenericService<T, ID> {

    @Autowired
    private CacheService cacheService;

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
        cacheService.updateCacheVersion();
        return repository.save(newT);
    }

    @Override
    public void deleteById(ID id) {
        cacheService.updateCacheVersion();
        repository.deleteById(id);
    }
}