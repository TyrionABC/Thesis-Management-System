package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user_id=#{UserId}")
    User selectUserById(String UserId);
    @Select("select * from user where user_id=#{userId} and password=#{password}")
    User selectUserByIdAndPassword(String userId,String password);

    @Update("update user set flag=1 where user_id=#{userId}")
    void deleteUserById(String userId);

    @Update("update user set permission=1-permission where user_id=#{userId}")
    void updateUserPermission(String userId);

    @Select("<script>" +
            "select * from user " +
            "<where>" +
            "<if test='user.userId!=null'>user_id=#{user.userId}</if>" +
            "<if test='user.name!=null'>and name=#{user.name}</if>" +
            "</where>" +
            "</script>")
    List<User> selectUsers(@Param("user") User user);

    @Select("select * from user where user_id=#{userId}")
    User test(String userId);
}
