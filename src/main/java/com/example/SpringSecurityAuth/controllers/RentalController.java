package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.entities.Rental;
import com.example.SpringSecurityAuth.services.RentalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "bearerAuth")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createRental(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("surface") double surface,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {
        return rentalService.createRental(name, description, price, surface, picture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("surface") double surface,
            @RequestParam(value = "picture", required = false) MultipartFile picture)
    {
        return rentalService.updateRental(id, name, description, price, surface, picture);
    }
}

