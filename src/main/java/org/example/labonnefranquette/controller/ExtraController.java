package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.services.ExtraService;
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
    ExtraService extraService;

    @GetMapping("/")
    public ResponseEntity<List<Extra>> getAllExtra() {
        return new ResponseEntity<>(extraService.getAllExtra(), HttpStatus.OK);
    }
}
