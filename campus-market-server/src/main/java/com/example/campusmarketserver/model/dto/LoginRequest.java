package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
