package org.labonnefranquette.data.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/menu")
@Tag(name = "Admin Menu Controller", description = "Controller pour les interractions des administracteurs sur la création des menus.")
public class AdminMenuController {

    @Autowired
    GenericService<Menu, Long> menuService;
/*
La création et la suppression des menus n'est pas encore implémenté
    @PostMapping("/create")
    public ResponseEntity<Menu> createNewMenu(@RequestBody Menu menu,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (menu == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Menu>(menuService.create(menu), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id,
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        menuService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
*/
}