package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.ChatListResponse;
import com.example.campusmarketserver.model.entity.Chat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMapper {
    @Insert("INSERT INTO chat ( sender_id, receiver_id, product_id ) VALUES (#{senderId}, #{receiverId}, #{productId})")
    int saveChat(@Param("senderId") int senderId, @Param("receiverId") int receiverId, @Param("productId") int productId);

    @Select("""
                SELECT
                    chat.id,
                    chat.product_id,
                    COALESCE(product.product_name, product_backup.product_name) AS product_name,
            
                    CASE
                        WHEN chat.sender_id = #{userId} THEN chat.sender_id
                        ELSE chat.receiver_id
                    END AS sender_id,
            
                    CASE
                        WHEN chat.sender_id = #{userId} THEN chat.receiver_id
                        ELSE chat.sender_id
                    END AS receiver_id,
            
                    CASE
                        WHEN chat.sender_id = #{userId} THEN receiver.user_name
                        ELSE sender.user_name
                    END AS chat_partner_name
            
                FROM
                    chat
                    LEFT JOIN product ON chat.product_id = product.id
                    LEFT JOIN product_backup ON chat.product_id = product_backup.id
                    JOIN `user` AS sender ON chat.sender_id = sender.id
                    JOIN `user` AS receiver ON chat.receiver_id = receiver.id
                WHERE
                    chat.sender_id = #{userId}
                    OR chat.receiver_id = #{userId}
            """)
    List<ChatListResponse> selectChatByUserId(@Param("userId") int userId);


    @Select("SELECT * FROM chat WHERE sender_id = #{senderId} AND receiver_id = #{receiverId} AND product_id = #{productId}")
    Chat selectChat(@Param("senderId") int senderId, @Param("receiverId") int receiverId, @Param("productId") int productId);
}
