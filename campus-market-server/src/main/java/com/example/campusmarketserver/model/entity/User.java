package com.example.campusmarketserver.model.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private double money;
}
