package com.example.campusmarketserver.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;

@Data
public class MessageRequest {
    private String senderId;
    private String receiverId;
    private String MessageContent;
    private String chatId;

    public static MessageRequest fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json,MessageRequest.class);
    }
}
