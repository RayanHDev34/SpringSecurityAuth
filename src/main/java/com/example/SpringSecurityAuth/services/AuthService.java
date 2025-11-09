package com.example.SpringSecurityAuth.services;

import com.example.SpringSecurityAuth.dto.LoginRequest;
import com.example.SpringSecurityAuth.dto.RegisterRequest;
import com.example.SpringSecurityAuth.dto.TokenResponse;
import com.example.SpringSecurityAuth.dto.UserResponse;
import com.example.SpringSecurityAuth.entities.User;
import com.example.SpringSecurityAuth.mapper.UserMapper;
import com.example.SpringSecurityAuth.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->  new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new TokenResponse(token);
    }

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = UserMapper.fromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new TokenResponse(token);
    }

    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse response = UserMapper.toDto(user);
        return response;
    }
}
