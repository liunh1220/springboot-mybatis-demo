package com.example.demo.config.ds;

/**
 * Created by Administrator on 2018/5/20.
 */
public interface MultipleDataSourceInitializer {

    MultipleDataSourceInitializer DEFAULT = new Default();

    String BEAN_NAME = "multipleDataSourceInitializer";


    class Default implements MultipleDataSourceInitializer {

    }


}
