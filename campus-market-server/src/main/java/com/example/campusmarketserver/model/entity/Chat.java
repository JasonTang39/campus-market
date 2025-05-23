package com.example.campusmarketserver.model.entity;

import lombok.Data;

@Data
public class Chat {
    private int id;
    private int senderId;
    private int receiverId;
    private int productId;
}
