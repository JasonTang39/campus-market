package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private int id;
    private int orderStatus;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productImageUrl;
}
