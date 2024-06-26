package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/extra")
public class ExtraController {

    @Autowired
    GenericService<Extra, Long> extraService;

    @GetMapping
    public ResponseEntity<List<Extra>> getAllExtra() {
        return new ResponseEntity<>(extraService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Extra> getExtraById(@PathVariable long id) {
        Optional<Extra> extraFound = extraService.findAllById(id);
        return extraFound.map(extra -> new ResponseEntity<>(extra, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}