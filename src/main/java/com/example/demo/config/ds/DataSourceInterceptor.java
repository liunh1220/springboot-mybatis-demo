package com.example.demo.config.ds;

import com.example.demo.config.ds.sharding.ShardingTableDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;

/**
 * Created by Administrator on 2018/5/20.
 */
//使用@Aspect注解将一个java类定义为切面类
//使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
//根据需要在切入点不同位置的切入内容
//使用@Before在切入点开始处切入内容
//使用@After在切入点结尾处切入内容
//使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
//使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
//使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑

@Aspect
public class DataSourceInterceptor implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);
    private int order;

    @Value("11")
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Pointcut(value = "execution(public * com.example.demo..*.service..*.*(..))")
    public void anyPublicMethod() {

    }

    @Around("@annotation(ds)")
    public Object proceed(ProceedingJoinPoint pjp, com.example.demo.config.ds.DataSource ds) throws Throwable {
        DataSourceHolder.putServiceDataSource(ds.value());
        ShardingTableDataSourceHolder.putDataSourceName(ds.value());
        try {
            return pjp.proceed();
        } finally {
            DataSourceHolder.removeServiceDataSource(ds.value());
            ShardingTableDataSourceHolder.clear();
        }
    }
}