package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.dto.RentalRequest;
import com.example.SpringSecurityAuth.dto.RentalResponse;
import com.example.SpringSecurityAuth.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Locations", description = "Endpoints pour la gestion des locations (création, modification, récupération).")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(
            summary = "Obtenir toutes les locations",
            description = "Renvoie la liste complète des locations disponibles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })
    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        List<RentalResponse> rentalResponseList = rentalService.getAllRentals();
        return  ResponseEntity.ok(rentalResponseList);
    }

    @Operation(
            summary = "Obtenir une location par ID",
            description = "Renvoie les détails d'une location spécifique via son identifiant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location trouvée."),
            @ApiResponse(responseCode = "404", description = "Location non trouvée."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(
            @Parameter(description = "ID de la location à récupérer", example = "1")
            @PathVariable Long id) {
        RentalResponse rentalResponse = rentalService.getRentalById(id);
        return ResponseEntity.ok(rentalResponse);
    }

    @Operation(
            summary = "Créer une nouvelle location",
            description = "Permet à un utilisateur connecté de publier une nouvelle annonce de location.",
            requestBody = @RequestBody(
                    description = "Détails de la location à créer",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Requête invalide ou champs manquants."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createRental(
            @AuthenticationPrincipal String email,
            RentalRequest rentalRequest)
    {
        try {
            rentalService.createRental(email, rentalRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Rental created successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create rental: " + e.getMessage()));
        }
    }

    @Operation(
            summary = "Mettre à jour une location existante",
            description = "Modifie les informations d’une location existante via son identifiant.",
            requestBody = @RequestBody(
                    description = "Champs de la location à mettre à jour",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Location non trouvée."),
            @ApiResponse(responseCode = "400", description = "Requête invalide."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateRental(
            @Parameter(description = "ID de la location à modifier", example = "3")
            @PathVariable Long id,
            RentalRequest rentalRequest)
     {
         try {
             rentalService.updateRental(id, rentalRequest);
             return ResponseEntity.status(HttpStatus.OK)
                     .body(Map.of("message", "Rental updated successfully!"));
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(Map.of("error", e.getMessage()));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(Map.of("error", "Failed to update rental: " + e.getMessage()));
         }
    }
}
