package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    GenericService<Menu, Long> menuService;

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenu() {
        return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> resultat = menuService.findAllById(id);
        return resultat.map(menu -> new ResponseEntity<>(menu, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
