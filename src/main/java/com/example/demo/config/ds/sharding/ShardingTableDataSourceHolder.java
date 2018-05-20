package com.example.demo.config.ds.sharding;

/**
 * Created by Administrator on 2018/5/9.
 */
public class ShardingTableDataSourceHolder {

    public static final ThreadLocal<String> DS = new ThreadLocal();

    public ShardingTableDataSourceHolder() {
    }

    public static String getDataSourceName() {
        return (String)DS.get();
    }

    public static void putDataSourceName(String dataSource) {
        DS.set(dataSource);
    }

    public static void clear() {
        DS.remove();
    }

}
