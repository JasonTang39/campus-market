package com.example.campusmarketserver.controller;

import com.example.campusmarketserver.model.dto.ProductDetailResponse;
import com.example.campusmarketserver.model.dto.PublishProductRequest;
import com.example.campusmarketserver.model.entity.Product;
import com.example.campusmarketserver.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/publish", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String publishProduct(
            @CookieValue("userId") int userId,
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice") Double productPrice,
            @RequestParam("image") MultipartFile productImage
    ) {

        PublishProductRequest publishProductRequest = new PublishProductRequest(productName, productDescription, productPrice, productImage);
        productService.publishProduct(userId, publishProductRequest);
        return "publish product successfully";
    }

    @GetMapping("/getProductDetail")
    public ProductDetailResponse getAllProductDetail(@RequestParam("productId") int productId) {
        return productService.getProductDetail(productId);
    }
}
