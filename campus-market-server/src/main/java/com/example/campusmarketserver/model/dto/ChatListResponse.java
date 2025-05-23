package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class ChatListResponse {
    private int id;
    private int senderId;
    private int receiverId;
    private int productId;
    private String productName;
    private String chatPartnerName;
}
