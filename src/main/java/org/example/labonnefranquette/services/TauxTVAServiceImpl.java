package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.TauxTVA;
import org.example.labonnefranquette.repository.TauxTVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TauxTVAServiceImpl implements TauxTVAService {

    @Autowired
    TauxTVARepository tauxTVARepository;

    @Override
    public List<TauxTVA> getAllTauxTVA() {
        return tauxTVARepository.findAll();
    }
}
