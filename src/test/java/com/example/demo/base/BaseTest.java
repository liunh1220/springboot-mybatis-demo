package com.example.demo.base;

import com.example.demo.SpringbootMybatisApplication;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by liunanhua on 2018/3/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMybatisApplication.class)
@ActiveProfiles(profiles = "dev")
public class BaseTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test1(){
        User record = new User();
        record.setId("1");
        record = userMapper.findUser(record);
        System.out.print(record);
    }


}
