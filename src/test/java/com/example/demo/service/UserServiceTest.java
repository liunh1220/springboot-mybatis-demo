package com.example.demo.service;

import com.example.demo.base.BaseTest;
import com.example.demo.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by liunanhua on 2018/3/27.
 */

public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userMapper;

    @Test
    public void test1(){
        User record = new User();
        record.setId("1");
        record = userMapper.findUser(record);
        System.out.println("=============================================================");
        System.out.println(record);
    }

    @Test
    public void test2(){
        User record = new User();
        record.setName("a");
        List list = userMapper.findUserList(record);
        System.out.println("=============================================================");
        System.out.println(list);
    }


















}
