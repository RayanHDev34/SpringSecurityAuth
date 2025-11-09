package com.example.SpringSecurityAuth.mapper;

import com.example.SpringSecurityAuth.dto.RegisterRequest;
import com.example.SpringSecurityAuth.dto.UserResponse;
import com.example.SpringSecurityAuth.entities.User;


public class UserMapper {

    public static UserResponse toDto(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    public static User fromRegisterRequest(RegisterRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // ⚠️ à encoder ensuite dans le service
        return user;
    }

    public static User fromUserResponse(UserResponse dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());

        return user;
    }
}
