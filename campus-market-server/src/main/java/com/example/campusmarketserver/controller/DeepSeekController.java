package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.service.DeepSeekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/deepSeek")
public class DeepSeekController {
    private final DeepSeekService deepSeekService;

    public DeepSeekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithDeepSeek(@RequestBody String userMessage, @CookieValue("userId") int userId) {
        String response = deepSeekService.getResponseFromDeepSeek(userMessage, userId);
        return ResponseEntity.ok(response);
    }

}
