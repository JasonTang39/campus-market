package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private int receiverId;
    private int productId;
}
