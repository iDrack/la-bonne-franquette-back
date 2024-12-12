package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.services.GenericService;
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
    GenericService<Menu, Long> menuService;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Menu>> getAllMenu() {
        return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
    }
/*
Les menus ne sont jamais récupéré par leur id
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> resultat = menuService.findAllById(id);
        return resultat.map(menu -> new ResponseEntity<>(menu, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
*/
}
