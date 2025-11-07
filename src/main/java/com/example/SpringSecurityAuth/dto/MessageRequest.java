package com.example.SpringSecurityAuth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête pour l'envoi d'un message à propos d'une location")
public class MessageRequest {

    @Schema(description = "Nom complet de l'expéditeur", example = "John Doe", required = true)
    private String name;

    @Schema(description = "Adresse e-mail de l'expéditeur", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Contenu du message", example = "Bonjour, je suis intéressé par votre appartement.", required = true)
    private String message;

    @Schema(description = "ID de la location concernée", example = "3", required = true)
    private Long rental_id;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRental_id() {
        return rental_id;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }
}
