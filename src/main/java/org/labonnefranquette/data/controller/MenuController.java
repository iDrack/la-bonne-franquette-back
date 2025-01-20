package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private GenericServiceImpl<Menu, MenuRepository, Long> menuService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Menu>> getAllMenu() {
        return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
    }
}