package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.ProductDetailResponse;
import com.example.campusmarketserver.model.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select(""" 
                SELECT
                	p.*
                FROM
                	product p,
                	user u1,
                	user u2
                WHERE
                	u1.id = #{userId}
                	AND u2.school_name = u1.school_name
                	AND p.seller_id = u2.id
                    AND u2.id != #{userId}
            """)
    List<Product> selectAll(@Param("userId") int userId);

    @Insert("INSERT INTO product " +
            "( seller_id, product_name, product_description, product_price, product_image_url )" +
            "VALUES" +
            "(#{sellerId}, #{productName}, #{productDescription}, #{productPrice}, #{productImageUrl})")
    int saveProduct(Product product);

    @Select("SELECT `user`.user_name AS seller_name,product.* FROM `user`, product WHERE user.id = product.seller_id AND product.id = #{productId}")
    ProductDetailResponse selectProductDetail(@Param("productId") int productId);


    @Select("SELECT * FROM product WHERE seller_id = #{userId}")
    List<Product> selectPublishedProducts(@Param("userId") int userId);

    @Insert("""
                INSERT INTO product_backup
                SELECT * FROM product WHERE id = #{productId}
            """)
    void backupProduct(@Param("productId") int productId);


    @Delete("DELETE FROM product WHERE id = #{productId}")
    void deleteProduct(@Param("productId") int productId);

    @Insert("INSERT INTO `order` (seller_id, buyer_id, product_id) VALUES (#{sellerId}, #{buyerId}, #{productId})")
    void insertOrder(@Param("sellerId") int sellerId, @Param("buyerId") int buyerId, @Param("productId") int productId);




}
