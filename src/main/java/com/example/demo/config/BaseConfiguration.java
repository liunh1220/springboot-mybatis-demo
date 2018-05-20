package com.example.demo.config;

import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.example.demo.base.DataSourceNames;
import com.example.demo.config.ds.MultipleDataSource;
import com.example.demo.config.ds.ShardingDataSourceCollector;
import com.example.demo.config.ds.sharding.read.ReadShardingDataSourceFactoryBean;
import com.example.demo.config.ds.sharding.write.RecordShardingDataSourceFactoryBean;
import com.example.demo.constant.ApplicationConstant;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liulanhua on 2018/3/19.
 */
@ComponentScan({"com.example.demo"})
@MapperScan(basePackages = "com.example.demo.dao")//mapper接口的路径
public class BaseConfiguration extends WebMvcConfigurerAdapter  {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }

    @Bean
    public MultipleDataSource multipleDataSource() {
        return new MultipleDataSource(Lists.newArrayList(DataSourceNames.DB_READ, DataSourceNames.DB_WRITE));
    }

    @Bean
    @Qualifier(DataSourceNames.SHARDING_RP_WRITE)
    public RecordShardingDataSourceFactoryBean recordShardingDataSource() {
        return new RecordShardingDataSourceFactoryBean();
    }


    @Bean
    @Qualifier(DataSourceNames.SHARDING_RP_READ)
    public ReadShardingDataSourceFactoryBean readShardingDataSource() {
        return new ReadShardingDataSourceFactoryBean();
    }

    @Bean
    public ShardingDataSourceCollector readShardingDataSourceCollector(
            @Qualifier(DataSourceNames.SHARDING_RP_READ) ShardingDataSource readShardingDataSource,
            @Qualifier(DataSourceNames.SHARDING_RP_WRITE) ShardingDataSource recordShardingDataSource) {
        return () -> {
            Map<String, ShardingDataSource> shardingDataSourceMap = new HashMap<>();
            shardingDataSourceMap.put(DataSourceNames.SHARDING_RP_READ, readShardingDataSource);
            shardingDataSourceMap.put(DataSourceNames.SHARDING_RP_WRITE, recordShardingDataSource);
            return shardingDataSourceMap;
        };
    }


    @Bean(name = "hostName")
    public String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "mybatis-demo";
    }

}
