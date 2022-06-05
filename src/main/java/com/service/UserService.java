package com.service;

import com.dao.UserMapper;
import com.domain.User;
import com.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//MD5Utils.code(password)
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User selectUserByUserIdAndPassword(String userId, String password) {
        User user=userMapper.selectUserByIdAndPassword(userId,password);
        if(user==null) {
            return null;
        }
        else {
            if (user.getPassword().equals(password)) {
                return user;
            }
            else{
                return null;
            }
        }
    }
    public User selectUserById(String userId){
        return userMapper.selectUserById(userId);
    }
    public void insertUser(User user){
        //user.setPassword(MD5Utils.code(user.getPassword()));//加密
        userMapper.insert(user);
    }
    public void deleteUserByUserId(String userId){
        userMapper.deleteUserById(userId);
    }
    public void updateUser(User user){
        userMapper.updateById(user);
    }
    public List<User> getAll(){
        return userMapper.selectList(null);
    }
    public List<User> getUsers(User user){
        return userMapper.selectUsers(user);
    }

    public void updateAccess(String userId){
        userMapper.updateUserPermission(userId);
    }
}
