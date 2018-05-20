package com.example.demo;

import com.example.demo.config.BaseConfiguration;
import com.example.demo.config.ds.MultipleDataSourceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import({BaseConfiguration.class, MultipleDataSourceConfiguration.class})
@EnableAsync
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }
}
