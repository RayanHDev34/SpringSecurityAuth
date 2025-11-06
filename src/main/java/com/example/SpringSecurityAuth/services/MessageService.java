package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.entities.Message;
import com.example.SpringSecurityAuth.entities.User;
import com.example.SpringSecurityAuth.repositories.MessageRepository;
import com.example.SpringSecurityAuth.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
    }

    public ResponseEntity<Map<String, String>> createMessage(Map<String, Object> json) {
        Long rentalId = Long.valueOf(json.get("rental_id").toString());
        Long userId = Long.valueOf(json.get("user_id").toString());
        String messageText = json.get("message").toString();

        Message message = new Message();
        message.setRentalId(rentalId);
        message.setUserId(userId);
        message.setMessage(messageText);

        messageRepository.save(message);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
