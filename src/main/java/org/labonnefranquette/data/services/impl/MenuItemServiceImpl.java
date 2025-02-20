package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.repository.MenuItemRepository;
import org.labonnefranquette.data.services.CacheService;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements GenericService<MenuItem, Long> {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    private CacheService cacheService;

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> findAllById(Long id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public MenuItem create(MenuItem newMenuItem) {
        cacheService.updateCacheVersion();
        return menuItemRepository.save(newMenuItem);
    }

    @Override
    public void deleteById(Long id) {
        cacheService.updateCacheVersion();
        menuItemRepository.deleteById(id);
    }
}
