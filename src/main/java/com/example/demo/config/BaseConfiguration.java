package com.example.demo.config;

import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.example.demo.base.DataSourceNames;
import com.example.demo.config.ds.MultipleDataSource;
import com.example.demo.config.ds.ShardingDataSourceCollector;
import com.example.demo.config.ds.sharding.RecordShardingDataSourceFactoryBean;
import com.example.demo.constant.ApplicationConstant;
import com.example.demo.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import okhttp3.OkHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.util.Constants.RAW_REST_TEMPLATE;

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
    public ApplicationContextHolder applicationContextHolder(ApplicationConstant applicationConstant) {
        ApplicationContextHolder applicationContextHolder = ApplicationContextHolder.getInstance();
        applicationContextHolder.init(applicationConstant);
        return applicationContextHolder;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JacksonUtils.mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

    @Primary
    @Bean
    public okhttp3.OkHttpClient okHttpClient(ApplicationConstant applicationConstant){
        return OkHttpUtils.okHttpClientBuilder(applicationConstant).build();
    }

    @LoadBalanced
    @Bean
    @Primary
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
                                     OkHttpClient okHttpClient) {
        return EnhancedRestTemplate.assembleRestTemplate(mappingJackson2HttpMessageConverter, okHttpClient);
    }

    @Bean(name = RAW_REST_TEMPLATE)
    public RestTemplate rawRestTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
                                        OkHttpClient okHttpClient) {

        return EnhancedRestTemplate.assembleRestTemplate(mappingJackson2HttpMessageConverter, okHttpClient);
    }

    @Bean
    public RefreshScope scope(){
        return new RefreshScope();
    }

    @Bean
    public ContextRefresher contextRefresher(ConfigurableApplicationContext context,
                                             RefreshScope scope) {
        return new MyContextRefresher(context, scope);
    }



    @Bean
    public MultipleDataSource multipleDataSource() {
        return new MultipleDataSource(Lists.newArrayList(DataSourceNames.DB_READ, DataSourceNames.DB_WRITE));
    }

    @Bean
    @Qualifier(DataSourceNames.SHARDING_RP_WRITE)
    public RecordShardingDataSourceFactoryBean recordShardingDataSource() {
        return new RecordShardingDataSourceFactoryBean(Boolean.TRUE);
    }

    @Bean
    @Qualifier(DataSourceNames.SHARDING_RP_READ)
    public RecordShardingDataSourceFactoryBean readShardingDataSource() {
        return new RecordShardingDataSourceFactoryBean(Boolean.FALSE);
    }

    /*@Bean
    @Qualifier(DataSourceNames.SHARDING_RP_READ)
    public ReadShardingDataSourceFactoryBean readShardingDataSource() {
        return new ReadShardingDataSourceFactoryBean();
    }*/

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
