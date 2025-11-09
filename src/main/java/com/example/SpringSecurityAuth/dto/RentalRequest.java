package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête pour créer une location")
public class RentalRequest {
    private String name;
    private String description;
    private double price;
    private double surface;
    private MultipartFile picture;
}
