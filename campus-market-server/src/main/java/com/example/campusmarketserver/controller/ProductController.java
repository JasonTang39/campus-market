package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.entity.Product;
import com.example.campusmarketserver.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }
}
