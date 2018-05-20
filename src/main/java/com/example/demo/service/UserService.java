package com.example.demo.service;

import com.example.demo.config.ds.DataSource;
import com.example.demo.base.DataSourceNames;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
	private UserMapper userMapper;

    @DataSource(DataSourceNames.SHARDING_RP_WRITE)
    int insertUser(User record){
        return userMapper.insertUser(record);
    }

    @DataSource(DataSourceNames.SHARDING_RP_READ)
    User findUser(User record){
        return userMapper.findUser(record);
    }

    @DataSource(DataSourceNames.SHARDING_RP_WRITE)
    int updateUser(User record){
        return userMapper.updateUser(record);
    }

    @DataSource(DataSourceNames.SHARDING_RP_READ)
    int countUserByName(String name){
        return userMapper.countUserByName(name);
    }

    @DataSource(DataSourceNames.SHARDING_RP_READ)
    List<User> findUserList(User record){
        return userMapper.findUserList(record);
    }


}