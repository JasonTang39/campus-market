package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.dto.LoginRequest;
import com.example.campusmarketserver.model.dto.RegisterRequest;
import com.example.campusmarketserver.model.entity.User;
import com.example.campusmarketserver.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = userService.login(loginRequest);
        if (user != null) {
            Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            return ResponseEntity.ok("login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
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

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(@CookieValue("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
