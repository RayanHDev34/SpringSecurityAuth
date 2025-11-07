package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête de connexion d’un utilisateur")
public class LoginRequest {

    @Schema(description = "Adresse e-mail de l'utilisateur", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "mypassword123", required = true)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
