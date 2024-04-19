package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.repository.ExtraRepository;
import org.example.labonnefranquette.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtraServiceImpl implements GenericService<Extra, Long> {

    @Autowired
    ExtraRepository extraRepository;

    @Override
    public List<Extra> findAll() {
        return extraRepository.findAll();
    }

    @Override
    public Optional<Extra> findAllById(Long id) {
        return extraRepository.findById(id);
    }
}
