package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/menu")
public class AdminMenuController {

    @Autowired
    GenericService<Menu, Long> menuService;
/*
La création et la suppression des menus n'est pas encore implémenté
    @PostMapping("/create")
    public ResponseEntity<Menu> createNewMenu(@RequestBody Menu menu) {
        if (menu == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Menu>(menuService.create(menu), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        menuService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
*/
}