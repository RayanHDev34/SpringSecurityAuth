package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données de réponse pour une location")
public class RentalResponse {

    private Long id;

    @Schema(description = "Identifiant du propriétaire", example = "1")
    private Long ownerId;

    @Schema(description = "Nom de la location", example = "Appartement T3 à Montpellier")
    private String name;

    @Schema(description = "Description de la location", example = "Appartement lumineux avec terrasse")
    private String description;

    @Schema(description = "Prix mensuel de la location", example = "850")
    private double price;

    @Schema(description = "Surface du logement en m²", example = "75")
    private double surface;

    @Schema(description = "URL de l'image principale de la location")
    private String picture;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
