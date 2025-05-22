package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.UserMapper;
import com.example.campusmarketserver.model.dto.LoginRequest;
import com.example.campusmarketserver.model.dto.RegisterRequest;
import com.example.campusmarketserver.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // login
    public Boolean login(LoginRequest loginRequest) {
        User user = userMapper.selectByName(loginRequest.getUserName());
        if (user != null && loginRequest.getPassword().equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    // register
    public Boolean register(RegisterRequest registerRequest) {
        if (userMapper.selectByName(registerRequest.getUserName()) == null) {
            // user does not exist
            int rows = userMapper.insertUser(registerRequest);
            return true;
        } else {
            return false;
        }
    }
}
