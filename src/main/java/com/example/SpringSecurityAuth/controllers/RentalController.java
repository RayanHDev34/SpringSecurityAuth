package com.example.SpringSecurityAuth.controllers;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Map<String, Object>>> getAllRentals() {
        return rentalService.getAllRentals();
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
    public ResponseEntity<Map<String, Object>> getRentalById(
            @Parameter(description = "ID de la location à récupérer", example = "1")
            @PathVariable Long id) {
        return rentalService.getRentalById(id);
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
            @Parameter(description = "Nom de la location", example = "Appartement T3 à Montpellier")
            @RequestParam("name") String name,

            @Parameter(description = "Description de la location", example = "Appartement lumineux avec terrasse et garage.")
            @RequestParam("description") String description,

            @Parameter(description = "Prix mensuel de la location", example = "850")
            @RequestParam("price") double price,

            @Parameter(description = "Surface du logement en m²", example = "75")
            @RequestParam("surface") double surface,

            @Parameter(description = "Image principale de la location (facultative)")
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        return rentalService.createRental(name, description, price, surface, picture);
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

            @Parameter(description = "Nom de la location", example = "Studio rénové centre-ville")
            @RequestParam("name") String name,

            @Parameter(description = "Description de la location", example = "Studio tout équipé proche de la gare.")
            @RequestParam("description") String description,

            @Parameter(description = "Prix mensuel de la location", example = "650")
            @RequestParam("price") double price,

            @Parameter(description = "Surface du logement en m²", example = "30")
            @RequestParam("surface") double surface,

            @Parameter(description = "Nouvelle image (facultative)")
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        return rentalService.updateRental(id, name, description, price, surface, picture);
    }
}
