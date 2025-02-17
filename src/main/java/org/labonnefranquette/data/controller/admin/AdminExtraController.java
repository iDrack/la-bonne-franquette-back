package org.labonnefranquette.data.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/extra")
@Tag(name = "Admin Extra Controller", description = "Controller pour les interractions des administracteurs sur la création des extras.")
public class AdminExtraController {

    @Autowired
    GenericService<Extra, Long> extraService;
/*
La gestion des extras n'est pas encore implémenté dans l'application

    @PostMapping("/create")
    public ResponseEntity<Extra> createNewExtra(@RequestBody Extra extra) {
        if (extra == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Extra>(extraService.create(extra), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExtra(@PathVariable Long id) {
        extraService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
*/
}