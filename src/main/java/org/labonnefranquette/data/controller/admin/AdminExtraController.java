package org.labonnefranquette.data.controller.admin;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/extra")
public class AdminExtraController {

    @Autowired
    GenericService<Extra, Long> extraService;

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
}