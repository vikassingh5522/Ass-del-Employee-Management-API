package com.deloitte.employee_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Health Check Controller.
 * Provides endpoints to verify that the API is running.
 */
@RestController
public class HealthController {

    // GET / - Root endpoint, returns API status and service name
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Employee Management API"
        ));
    }

    // GET /health - Health check endpoint, returns API status
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
