package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.dto.MessageRequest;
import com.example.SpringSecurityAuth.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
            description = "Permet à un utilisateur connecté d’envoyer un message au propriétaire d’une location.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Corps de la requête contenant les informations du message",
                    content = @Content(schema = @Schema(implementation = MessageRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès."),
            @ApiResponse(responseCode = "400", description = "Requête invalide."),
            @ApiResponse(responseCode = "401", description = "Non autorisé — token manquant ou invalide.")
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@RequestBody MessageRequest request) {
        return messageService.createMessage(Map.of(
                "name", request.getName(),
                "email", request.getEmail(),
                "message", request.getMessage(),
                "rental_id", request.getRental_id()
        ));
    }
}
