package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.dto.RentalRequest;
import com.example.SpringSecurityAuth.dto.RentalResponse;
import com.example.SpringSecurityAuth.entities.Rental;
import com.example.SpringSecurityAuth.entities.User;
import com.example.SpringSecurityAuth.mapper.RentalMapper;
import com.example.SpringSecurityAuth.repositories.RentalRepository;
import com.example.SpringSecurityAuth.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, FileUploadService fileUploadService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
    }

    public List<RentalResponse> getAllRentals() {
        List<RentalResponse> rentalResponseList = rentalRepository
                .findAll()
                .stream()
                .map(RentalMapper::toDto)
                .toList();
        return rentalResponseList;
    }

    public RentalResponse getRentalById(Long id) {
        return rentalRepository.findById(id)
                .map(RentalMapper::toDto)
                .orElseGet(RentalResponse::new);
    }

    public void createRental(String email, RentalRequest rentalRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Rental rental = RentalMapper.fromRequest(rentalRequest);
        rental.setOwnerId(user.getId());
        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            String imageUrl = fileUploadService.uploadToCloudinary(rentalRequest.getPicture());
            rental.setPicture(imageUrl);
        }
        rentalRepository.save(rental);
    }

    public void updateRental(
            Long id,
            RentalRequest rentalRequest)
    {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        RentalMapper.updateEntityFromRequest(rental, rentalRequest);
        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            String imageUrl = fileUploadService.uploadToCloudinary(rentalRequest.getPicture());
            rental.setPicture(imageUrl);
        }
        rentalRepository.save(rental);
    }
}
