package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.service.DeepSeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/deepSeek")
public class DeepSeekController {
    private final DeepSeekService deepSeekService;

    public DeepSeekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithDeepSeek(@RequestBody String userMessage) {
        String response = deepSeekService.getResponseFromDeepSeek(userMessage);
        return ResponseEntity.ok(response);
    }

}
