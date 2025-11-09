package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.dto.LoginRequest;
import com.example.SpringSecurityAuth.dto.RegisterRequest;
import com.example.SpringSecurityAuth.dto.TokenResponse;
import com.example.SpringSecurityAuth.dto.UserResponse;
import com.example.SpringSecurityAuth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentification",
        description = "Endpoints pour l'inscription, la connexion et la récupération du profil utilisateur.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Connexion d’un utilisateur",
            description = "Permet à un utilisateur existant de se connecter et d’obtenir un token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie — renvoie le token JWT."),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides."),
            @ApiResponse(responseCode = "400", description = "Requête mal formée.")
    })
    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            TokenResponse tokenResponse = authService.login(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    @Operation(
            summary = "Inscription d’un nouvel utilisateur",
            description = "Crée un nouvel utilisateur dans le système et renvoie un message de confirmation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription réussie."),
            @ApiResponse(responseCode = "400", description = "Erreur de validation ou données manquantes.")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            TokenResponse tokenResponse = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    @Operation(
            summary = "Récupération du profil de l’utilisateur connecté",
            description = "Renvoie les informations du profil correspondant au token JWT fourni.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil utilisateur récupéré avec succès."),
            @ApiResponse(responseCode = "401", description = "Token invalide ou manquant."),
            @ApiResponse(responseCode = "403", description = "Accès non autorisé.")
    })
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal String email) {
        UserResponse response =  authService.getCurrentUser(email);
        return ResponseEntity.ok(response);
    }
}
