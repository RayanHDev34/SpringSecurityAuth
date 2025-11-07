package com.example.SpringSecurityAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload pour envoyer un message lié à une location")
public class MessageRequest {
    @JsonProperty("user_id")
    @Schema(description = "ID de l'utilisateur expéditeur", example = "12", required = true)
    private Long user_id;
    @JsonProperty("message")
    @Schema(description = "Contenu du message", example = "Bonjour, votre logement m'intéresse !", required = true)
    private String message;
    @JsonProperty("rental_id")
    @Schema(description = "ID de la location concernée", example = "3", required = true)
    private Long rental_id;

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getRental_id() { return rental_id; }
    public void setRental_id(Long rental_id) { this.rental_id = rental_id; }
}
