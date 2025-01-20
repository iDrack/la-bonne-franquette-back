package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.repository.ExtraRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/extra")
public class ExtraController {

    @Autowired
    private GenericServiceImpl<Extra, ExtraRepository, Long> extraService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Extra>> getAllExtra() {
        return new ResponseEntity<>(extraService.findAll(), HttpStatus.OK);
    }
}