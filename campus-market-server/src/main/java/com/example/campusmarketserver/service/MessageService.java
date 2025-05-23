package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.MessageMapper;
import com.example.campusmarketserver.model.dto.MessageRequest;
import com.example.campusmarketserver.model.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void saveMessage(MessageRequest messageRequest){
        messageMapper.addMessage(messageRequest);
    }

    public List<Message> getMessageByChatId(int chatId){
        return messageMapper.selectByChatId(chatId);
    }
}
