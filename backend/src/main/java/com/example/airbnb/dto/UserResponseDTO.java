package com.example.airbnb.dto;

import com.example.airbnb.domain.User;

public class UserResponseDTO {

    private final String name;
    private final String email;
    private final String userId;
    private final String token;

    public UserResponseDTO(String name, String email, String userId, String token) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.token = token;
    }

    public static UserResponseDTO createUserResponseDTO(User user, String token) {
        return new UserResponseDTO(user.getName(), user.getEmail(), user.getUserId(), token);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
