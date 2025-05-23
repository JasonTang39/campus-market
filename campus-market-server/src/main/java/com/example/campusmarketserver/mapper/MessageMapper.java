package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.MessageRequest;
import com.example.campusmarketserver.model.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper {
    @Insert("INSERT INTO message(sender_id, receiver_id, message_content, chat_id) VALUES(#{senderId}, #{receiverId}, #{messageContent}, #{chatId})")
    void addMessage(MessageRequest messageRequest);

    @Select("SELECT * FROM message WHERE chat_id = #{chatId}")
    List<Message> selectByChatId(@Param("chatId") int chatId);
}
