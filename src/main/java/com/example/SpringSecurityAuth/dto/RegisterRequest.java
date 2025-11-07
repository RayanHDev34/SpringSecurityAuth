package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête d’inscription d’un nouvel utilisateur")
public class RegisterRequest {

    @Schema(description = "Nom complet de l'utilisateur", example = "John Doe", required = true)
    private String name;

    @Schema(description = "Adresse e-mail", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe", example = "mypassword123", required = true)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
