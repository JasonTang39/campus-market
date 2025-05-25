package com.example.campusmarketserver.model.dto;

import lombok.Data;

@Data
public class BuyProductRequest {
    private int sellerId;
    private int productId;
}
