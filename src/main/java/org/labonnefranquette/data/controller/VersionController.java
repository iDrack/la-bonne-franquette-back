package org.labonnefranquette.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.labonnefranquette.data.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/version")
public class VersionController {
    
    @Autowired
    private CacheService cacheService;
    @GetMapping("/cache")
    public ResponseEntity<String> getMethodName() {
         return new ResponseEntity<>(cacheService.getVersion(), HttpStatus.OK);
    }
    
}
