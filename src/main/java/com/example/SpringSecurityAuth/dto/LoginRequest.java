package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Requête de connexion d’un utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "Adresse e-mail de l'utilisateur", example = "test@test.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "test!31", required = true)
    private String password;
}
