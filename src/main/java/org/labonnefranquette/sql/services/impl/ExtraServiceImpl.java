package org.labonnefranquette.sql.services.impl;

import org.labonnefranquette.sql.model.Extra;
import org.labonnefranquette.sql.repository.ExtraRepository;
import org.labonnefranquette.sql.services.GenericService;
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
