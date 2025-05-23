package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.dto.ChatListResponse;
import com.example.campusmarketserver.model.dto.ChatRequest;
import com.example.campusmarketserver.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/save")
    public int saveChat(@CookieValue("userId") int userId, @RequestBody ChatRequest chatRequest) {
        return chatService.saveChat(userId, chatRequest.getReceiverId(), chatRequest.getProductId());
    }

    @GetMapping("/getChat")
    public List<ChatListResponse> getChatListByUserId(@CookieValue("userId") int userId){
        return chatService.getChatListByUserId(userId);
    }
}
