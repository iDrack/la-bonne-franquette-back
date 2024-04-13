package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.TauxTVA;
import org.example.labonnefranquette.services.TauxTVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/tauxTVA")
public class TauxTVAController {

    @Autowired
    TauxTVAService tauxTVAService;

    @GetMapping("/")
    public ResponseEntity<List<TauxTVA>> getAllTauxTVA() {
        return new ResponseEntity<>(tauxTVAService.getAllTauxTVA(), HttpStatus.OK);
    }
}
