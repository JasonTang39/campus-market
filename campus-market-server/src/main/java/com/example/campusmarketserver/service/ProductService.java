package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.ProductMapper;
import com.example.campusmarketserver.model.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getAllProduct() {
        return productMapper.selectAll();
    }
}
