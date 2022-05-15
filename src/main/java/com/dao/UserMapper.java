package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user_id=#{UserId}")
    User selectUserById(String UserId);
    @Select("select * from user where user_id=#{userId} and password=#{password}")
    User selectUserByIdAndPassword(String userId,String password);
    @Delete("delete from user where user_id=#{userId}")
    void deleteUserById(String userId);
}
