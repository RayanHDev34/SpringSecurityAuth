package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.dto.UserResponse;
import com.example.SpringSecurityAuth.entities.User;
import com.example.SpringSecurityAuth.mapper.UserMapper;
import com.example.SpringSecurityAuth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // ✅ récupération du User
            return UserMapper.toDto(user);

        } else {
            return new UserResponse(); // ✅ UserResponse vide
        }

    }
}
