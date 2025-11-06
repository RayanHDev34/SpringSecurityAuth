package com.example.SpringSecurityAuth.controllers;

import com.example.SpringSecurityAuth.entities.Message;
import com.example.SpringSecurityAuth.services.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@RequestBody Map<String, Object> json) {
        return messageService.createMessage(json);
    }
}
