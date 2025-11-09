package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.dto.MessageRequest;
import com.example.SpringSecurityAuth.entities.Message;
import com.example.SpringSecurityAuth.entities.User;
import com.example.SpringSecurityAuth.mapper.MessageMapper;
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

    public void createMessage(MessageRequest request) {
        // Vérifie que les IDs sont présents
        // Map DTO → Entity
        Message message = MessageMapper.fromRequest(request);

        // Sauvegarde en base
        messageRepository.save(message);
    }
}
