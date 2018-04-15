package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by liulanhua on 2018/3/19.
 */
@ComponentScan({"com.example.demo.service","com.example.demo.controller"})
@MapperScan(basePackages = "com.example.demo.dao")//mapper接口的路径
public class BaseConfiguration {


}
