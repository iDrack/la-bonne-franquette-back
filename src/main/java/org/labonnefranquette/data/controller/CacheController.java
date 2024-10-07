package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/cache")
public class CacheController {

    @Autowired
    CacheService cacheService;

    //Utilisé lors de l'initialisation de l'application
    @GetMapping("/version")
    public ResponseEntity<String> getCache() {
         return new ResponseEntity<>(CacheService.getVersion(), HttpStatus.OK);
    }

    //Utilisé dans la modale de paramètre
    @PostMapping("/rafraichir")
    public ResponseEntity<Boolean> refreshCache() {
        return new ResponseEntity<>(cacheService.clear(), HttpStatus.OK);
    }
    
}
