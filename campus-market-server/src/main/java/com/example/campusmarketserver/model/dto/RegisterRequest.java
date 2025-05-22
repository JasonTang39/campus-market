package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String password;
}
