package com.example.campusmarketserver.model.entity;

import lombok.Data;

@Data
public class Message {
    private int id;
    private String senderId;
    private String receiverId;
    private String MessageContent;
    private String chatId;
}
