package com.example.campusmarketserver.mapper;

import com.example.campusmarketserver.model.dto.RegisterRequest;
import com.example.campusmarketserver.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selectByName(@Param("userName") String username);

    @Insert("INSERT INTO user (user_name, password, school_name) VALUES (#{userName}, #{password}, #{schoolName})")
    int insertUser(RegisterRequest registerRequest);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User selectById(@Param("userId") int userId);
}
