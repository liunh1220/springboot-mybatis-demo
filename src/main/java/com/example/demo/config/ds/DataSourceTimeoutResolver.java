package com.example.demo.config.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20.
 */
public class DataSourceTimeoutResolver {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceTimeoutResolver.class);

    public static final String CONFIG_PREFIX = "app.ds.timeout.";

    public static final Map<String, Integer> DS_TIMEOUT_MAP = new HashMap<>();

    private static Integer PLACEHOLDER = -1;

    private volatile static ApplicationContext applicationContext;

    static {
        DS_TIMEOUT_MAP.put("db.ds.read.common", 12);
        DS_TIMEOUT_MAP.put("db.ds.read.real", 12);
        DS_TIMEOUT_MAP.put("db.ds.read.low", 60);
//        DS_TIMEOUT_MAP.put("db.ds.write.test", 6);
    }

    private static volatile Map<String, Integer> dsTimeoutMap = Collections.unmodifiableMap(new HashMap<>());

    public DataSourceTimeoutResolver(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public static Integer getDataSourceTimeoutInSeconds(String dataSource) {

        Integer timeout = dsTimeoutMap.get(dataSource);
        if(timeout == null) {
            //先从配置文件获取, 如果没有设置再从默认的map里获取.
            Environment env = applicationContext.getBean(Environment.class);
            timeout = env.getProperty(CONFIG_PREFIX + dataSource, Integer.class);

            if(timeout == null || timeout < 0) {
                timeout = DS_TIMEOUT_MAP.get(dataSource);
            }

            //查询完成, 替换map
            synchronized (DataSourceTimeoutResolver.class) {
                Map<String, Integer> map = new HashMap<>();
                map.putAll(dsTimeoutMap);
                map.put(dataSource, timeout == null ? PLACEHOLDER : timeout);
                dsTimeoutMap = Collections.unmodifiableMap(map);
            }
        }

        if(timeout != null && timeout.equals(PLACEHOLDER)) {
            timeout = null;
        }

        if(timeout != null && logger.isDebugEnabled()) {
            logger.debug("configure data source timeout, key: {}, timeout: {}s", CONFIG_PREFIX + dataSource, timeout);
        }

        return timeout;
    }

}
