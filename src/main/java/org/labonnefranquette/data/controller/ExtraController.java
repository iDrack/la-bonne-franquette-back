package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/extra")
public class ExtraController {

    @Autowired
    GenericService<Extra, Long> extraService;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Extra>> getAllExtra() {
        return new ResponseEntity<>(extraService.findAll(), HttpStatus.OK);
    }
/*
Les menus ne sont jamais récupéré par leur id
    @GetMapping("/{id}")
    public ResponseEntity<Extra> getExtraById(@PathVariable long id) {
        Optional<Extra> extraFound = extraService.findAllById(id);
        return extraFound.map(extra -> new ResponseEntity<>(extra, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }*/
}