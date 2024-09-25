package org.labonnefranquette.data.controller;

import org.labonnefranquette.data.cache.CacheService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/cache")
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping("/version")
    public ResponseEntity<String> getCache() {
         return new ResponseEntity<>(CacheService.getVersion(), HttpStatus.OK);
    }

    @PostMapping("/rafraichir")
    public ResponseEntity<Boolean> refreshCache() {
        return new ResponseEntity<>(cacheService.clear(), HttpStatus.OK);
    }
    
}
