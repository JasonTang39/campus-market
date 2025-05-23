package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.ChatMapper;
import com.example.campusmarketserver.model.dto.ChatListResponse;
import com.example.campusmarketserver.model.entity.Chat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatMapper chatMapper;

    public ChatService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public int saveChat(int senderId, int receiverId, int productId) {
        Chat chat = chatMapper.selectChat(senderId, receiverId, productId);
        if (chat == null) {
            return chatMapper.saveChat(senderId, receiverId, productId);
        } else {
            return 0;
        }
    }

    public List<ChatListResponse> getChatListByUserId(int userId) {
        return chatMapper.selectChatByUserId(userId);
    }
}
