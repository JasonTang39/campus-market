package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.OrderResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("""
                SELECT
                	o.id,
                	o.order_status,
                	pb.product_name,
                	pb.product_description,
                	pb.product_price,
                	pb.product_image_url
                FROM
                	`order` o,
                	product_backup pb
                WHERE
                	o.product_id = pb.id
                    AND o.seller_id = #{sellerId}
            """)
    List<OrderResponse> selectSoldProductOrders(@Param("sellerId") int sellerId);

    @Select("""
                SELECT
                	o.id,
                	o.order_status,
                	pb.product_name,
                	pb.product_description,
                	pb.product_price,
                	pb.product_image_url
                FROM
                	`order` o,
                	product_backup pb
                WHERE
                	o.product_id = pb.id
                    AND o.buyer_id = #{buyerId}
            """)
    List<OrderResponse> selectBoughtProductOrders(@Param("buyerId") int buyerId);


    @Update("UPDATE `order` SET order_status = '1' WHERE id = #{orderId}")
    void updateOrderStatus(@Param("orderId") int orderId);
}
