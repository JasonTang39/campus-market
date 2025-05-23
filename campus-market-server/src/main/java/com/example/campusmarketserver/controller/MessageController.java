package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.entity.Message;
import com.example.campusmarketserver.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("getMessageByChatId")
    public List<Message> getMessageByChatId(@RequestParam("chatId") int chatId){
        return messageService.getMessageByChatId(chatId);
    }
}
