package com.example.campusmarketserver.mapper;


import com.example.campusmarketserver.model.dto.DeepSeekData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeepSeekMapper {
    @Select(""" 
                SELECT
                	u2.user_name AS seller_name,
                	p.product_name,
                	p.product_description,
                	p.product_price
                FROM
                	product p,
                	`user` u1,
                	`user` u2
                WHERE
                	u1.id = #{userId}
                	AND u2.school_name = u1.school_name
                	AND p.seller_id = u2.id
            """)
    List<DeepSeekData> selectData(@Param("userId") int userId);
}
