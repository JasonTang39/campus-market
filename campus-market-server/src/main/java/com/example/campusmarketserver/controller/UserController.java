package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.dto.LoginRequest;
import com.example.campusmarketserver.model.dto.RegisterRequest;
import com.example.campusmarketserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean success = userService.login(loginRequest);
        if (success) {
            return ResponseEntity.ok("login successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user does not exist or wrong password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean success = userService.register(registerRequest);
        if (success) {
            return ResponseEntity.ok("register successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user has existed");
        }
    }

}
