package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.dto.LoginRequest;
import com.example.SpringSecurityAuth.dto.RegisterRequest;
import com.example.SpringSecurityAuth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        return authService.login(Map.of(
                "email", request.getEmail(),
                "password", request.getPassword()
        ));
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
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        return authService.register(Map.of(
                "name", request.getName(),
                "email", request.getEmail(),
                "password", request.getPassword()
        ));
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
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal String name) {
        return authService.getCurrentUser(name);
    }
}
