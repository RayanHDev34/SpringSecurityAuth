package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.entities.Rental;
import com.example.SpringSecurityAuth.repositories.RentalRepository;
import com.example.SpringSecurityAuth.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final FileUploadService fileUploadService;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository,  FileUploadService fileUploadService) {
        this.rentalRepository = rentalRepository;
        this.fileUploadService = fileUploadService;
    }

    public ResponseEntity<List<Map<String, Object>>>  getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        List<Map<String, Object>> response = rentals.stream().map(rental -> {
            Map<String, Object> rentalData = new HashMap<>();
            rentalData.put("id", rental.getId());
            rentalData.put("owner_id", rental.getOwnerId());
            rentalData.put("name", rental.getName());
            rentalData.put("description", rental.getDescription());
            rentalData.put("price", rental.getPrice());
            rentalData.put("surface", rental.getSurface());
            rentalData.put("picture", rental.getPicture());

            if (rental.getCreatedAt() != null) {
                rentalData.put("created_at", rental.getCreatedAt()
                        .toLocalDate()
                        .toString()
                        .replace("-", "/"));
            }
            if (rental.getUpdatedAt() != null) {
                rentalData.put("updated_at", rental.getUpdatedAt()
                        .toLocalDate()
                        .toString()
                        .replace("-", "/"));
            }

            return rentalData;
        }).toList();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> getRentalById(Long id) {
        return rentalRepository.findById(id)
                .map(rental -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", rental.getId());
                    response.put("name", rental.getName());
                    response.put("description", rental.getDescription());
                    response.put("price", rental.getPrice());
                    response.put("owner_id", rental.getOwnerId());
                    response.put("surface", rental.getSurface());
                    response.put("picture", rental.getPicture());

                    if (rental.getCreatedAt() != null) {
                        response.put("created_at", rental.getCreatedAt()
                                .toLocalDate()
                                .toString()
                                .replace("-", "/"));
                    }
                    if (rental.getUpdatedAt() != null) {
                        response.put("updated_at", rental.getUpdatedAt()
                                .toLocalDate()
                                .toString()
                                .replace("-", "/"));
                    }

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("message", "Rental not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    public ResponseEntity<Map<String, String>>  createRental(
            Long ownerId,
            String name,
            String description,
            double price,
            double surface,
            MultipartFile picture) {

        Rental rental = new Rental();
        rental.setOwnerId(ownerId);
        rental.setName(name);
        rental.setDescription(description);
        rental.setPrice(price);
        rental.setSurface(surface);

        if (picture != null && !picture.isEmpty()) {
            // ✅ Upload Cloudinary
            String imageUrl = fileUploadService.uploadToCloudinary(picture);
            rental.setPicture(imageUrl);
        }

        rentalRepository.save(rental);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    public ResponseEntity<Map<String, String>> updateRental(
            Long id,
            String name,
            String description,
            double price,
            double surface,
            MultipartFile picture) {

        return rentalRepository.findById(id)
                .map(rental -> {
                    // 🔹 Met à jour uniquement les champs fournis
                    if (name != null && !name.isBlank()) {
                        rental.setName(name);
                    }
                    if (description != null && !description.isBlank()) {
                        rental.setDescription(description);
                    }
                    if (price > 0) {
                        rental.setPrice(price);
                    }
                    if (surface > 0) {
                        rental.setSurface(surface);
                    }

                    // 🔹 Si une nouvelle image est envoyée → remplacer sur Cloudinary
                    if (picture != null && !picture.isEmpty()) {
                        String uploadedUrl = fileUploadService.replaceImage(rental.getPicture(), picture);
                        rental.setPicture(uploadedUrl);
                    }

                    rentalRepository.save(rental);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Rental updated successfully!");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                })
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Rental not found!");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }
}
