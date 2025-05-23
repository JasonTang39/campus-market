package com.example.campusmarketserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PublishProductRequest {
    private String productName;
    private String productDescription;
    private double productPrice;
    private MultipartFile productImage;
}
