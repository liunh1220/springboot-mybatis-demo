package com.example.demo.service;

import com.example.demo.base.BaseTest;
import com.example.demo.config.ds.sharding.ShardingTableRule;
import com.example.demo.model.User;
import com.example.demo.util.KeyGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by liunanhua on 2018/3/27.
 */

public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userService;


    @Test
    public void test1() {
        User record = new User();
        record.setId("732B738EAB1B45FAA176C02F23E9960B");
        record = userService.findUser(record);
        System.out.println("=============================================================");
        System.out.println(record);
    }

    @Test
    public void test2() {
        User record = new User();
        record.setName("a");
        List list = userService.findUserList(record);
        System.out.println("=============================================================");
        System.out.println(list);
    }

    @Test
    public void test3() {
        String uuid = KeyGenerator.getUUID();
        System.out.println("uuid= "+ uuid);
        System.out.println("logicTableName= "+ ShardingTableRule.generateTableName("t_user",uuid));
        User record = new User();
        record.setId(uuid);
        record.setName("a");
        record.setPassword("111111");
        int insertUser = userService.insertUser(record);
        System.out.println("=============================================================");
        System.out.println(insertUser);

    }


}
