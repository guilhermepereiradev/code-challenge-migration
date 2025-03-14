package com.example.dummyjson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    ResponseEntity<HashMap<String, String>> checkHealth() {
        HashMap<String, String> response = new HashMap<>();
        response.put("service", "dummyjson-client");
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}
