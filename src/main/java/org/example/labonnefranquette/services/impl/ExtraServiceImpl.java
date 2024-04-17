package org.example.labonnefranquette.services.impl;

import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.repository.ExtraRepository;
import org.example.labonnefranquette.services.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    ExtraRepository extraRepository;

    @Override
    public List<Extra> getAllExtra() {
        return extraRepository.findAll();
    }
}
