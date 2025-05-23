package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.ProductDetailResponse;
import com.example.campusmarketserver.model.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM product")
    List<Product> selectAll();

    @Insert("INSERT INTO product " +
            "( seller_id, product_name, product_description, product_price, product_image_url )" +
            "VALUES" +
            "(#{sellerId}, #{productName}, #{productDescription}, #{productPrice}, #{productImageUrl})")
    int saveProduct(Product product);

    @Select("SELECT `user`.user_name AS seller_name,product.* FROM `user`, product WHERE user.id = product.seller_id AND product.id = #{productId}")
    ProductDetailResponse selectProductDetail(@Param("productId") int productId);
}
