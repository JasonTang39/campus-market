package com.example.campusmarketserver.model.entity;

import lombok.Data;

@Data
public class Product {
    private int id;
    private int sellerId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productImage;
}
