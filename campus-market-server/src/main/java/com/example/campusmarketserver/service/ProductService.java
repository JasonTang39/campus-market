package com.example.campusmarketserver.service;

import com.example.campusmarketserver.mapper.ProductMapper;
import com.example.campusmarketserver.model.dto.ProductDetailResponse;
import com.example.campusmarketserver.model.dto.PublishProductRequest;
import com.example.campusmarketserver.model.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;


    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getAllProduct(int userId) {
        return productMapper.selectAll(userId);
    }


    public void publishProduct(int sellerId, PublishProductRequest publishProductRequest) {
        MultipartFile productImage = publishProductRequest.getProductImage();
        String originalFilename = productImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = System.currentTimeMillis() + suffix;
        String savePath = "D:/DevProjects/campus-market/campus-market-client/src/static/productImage/" + newFileName;
        String productImageUrl = "/src/static/productImage/" + newFileName;
        try {
            productImage.transferTo(new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product();
        product.setSellerId(sellerId);
        product.setProductName(publishProductRequest.getProductName());
        product.setProductDescription(publishProductRequest.getProductDescription());
        product.setProductPrice(publishProductRequest.getProductPrice());
        product.setProductImageUrl(productImageUrl);

        int rows = productMapper.saveProduct(product);
    }

    public ProductDetailResponse getProductDetail(int productId) {
        return productMapper.selectProductDetail(productId);
    }

    public List<Product> getPublishedProducts(int userId) {
        return productMapper.selectPublishedProducts(userId);
    }

    @Transactional
    public void backupAndDeleteProduct(int sellerId, int buyerId, int productId) {
        productMapper.backupProduct(productId);
        productMapper.deleteProduct(productId);
        productMapper.insertOrder(sellerId, buyerId, productId);
    }

    public void removeProduct(int productId) {
        productMapper.deleteProduct(productId);
    }
}
