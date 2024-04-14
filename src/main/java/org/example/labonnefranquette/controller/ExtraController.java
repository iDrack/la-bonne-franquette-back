package org.example.labonnefranquette.controller;

import org.example.labonnefranquette.model.Extra;
import org.example.labonnefranquette.services.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/extra")
public class ExtraController {

    @Autowired
    ExtraService extraService;

    @GetMapping("/")
    public List<Extra> getAllExtra() {
        return extraService.getAllExtra();
    }
}
