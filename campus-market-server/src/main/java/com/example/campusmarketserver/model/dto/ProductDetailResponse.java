package com.example.campusmarketserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailResponse {
    private String sellerName;
    private int id;
    private int sellerId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productImageUrl;
}
