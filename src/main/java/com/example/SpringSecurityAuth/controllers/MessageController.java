package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.dto.MessageRequest;
import com.example.SpringSecurityAuth.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Messages", description = "Endpoints pour l'envoi de messages à propos d'une location.")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(
            summary = "Envoyer un message concernant une location",
            description = "Permet à un utilisateur authentifié d’envoyer un message lié à une location."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès."),
            @ApiResponse(responseCode = "400", description = "Requête invalide."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })

    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(
            @RequestBody MessageRequest request
    ) {
        try {
            messageService.createMessage(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Message sent successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to send message: " + e.getMessage()));
        }
    }
}
