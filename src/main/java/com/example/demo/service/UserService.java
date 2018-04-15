package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
	private UserMapper userMapper;


    @Transactional
    int deleteUserById(String id){
        return userMapper.deleteUserById(id);
    }

    @Transactional
    int insertUser(User record){
        return userMapper.insertUser(record);
    }

    User findUser(User record){
        return userMapper.findUser(record);
    }

    @Transactional
    int updateUser(User record){
        return userMapper.updateUser(record);
    }
    
    int countUserByName(String name){
        return userMapper.countUserByName(name);
    }
    
    List<User> findUserList(User record){
        return userMapper.findUserList(record);
    }


}