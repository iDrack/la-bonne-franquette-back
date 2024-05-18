package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements GenericService<Menu, Long> {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> findAllById(Long id) {
        return menuRepository.findById(id);
    }
}
